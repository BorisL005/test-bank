package com.test.bank.service;

import com.test.bank.Application;
import com.test.bank.domain.dto.OpenCurrentAccountDTO;
import com.test.bank.domain.dto.ReportDTO;
import com.test.bank.domain.dto.TransactionDTO;
import com.test.bank.domain.model.Account;
import com.test.bank.domain.model.Customer;
import com.test.bank.domain.model.Transaction;
import com.test.bank.service.AccountService;
import com.test.bank.service.BankServiceFacade;
import com.test.bank.service.CustomerService;
import com.test.bank.service.TransactionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.test.bank.domain.model.enums.TransactionStatus.APPROVED;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/*******************************************************************************
 *
 * @author : <a href="mailto:borislepeshenkov@gmail.com">Boris Lepeshenkov</a>
 * @since : 15.03.2021
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class BankServiceFacadeTest {

    @Autowired
    private BankServiceFacade bankServiceFacade;
    @MockBean
    private AccountService accountService;
    @MockBean
    private CustomerService customerService;
    @MockBean
    private TransactionService transactionService;


    @Test
    public void openNewCurrentAccountWithTransaction() {
        testOpenNewCurrentAccount(BigDecimal.TEN, BigDecimal.TEN, 1);
    }

    @Test
    public void openNewCurrentAccountWithoutTransaction() {
        testOpenNewCurrentAccount(BigDecimal.TEN.negate(), BigDecimal.ZERO, 0);
    }

    private void testOpenNewCurrentAccount(BigDecimal inputAmount, BigDecimal expectedAmount, int invokeTimes) {
        final Customer customer = new Customer();
        customer.setId("12345");
        final Account account = new Account();
        account.setId("54321");
        when(customerService.validateCustomerAndFetch("12345")).thenReturn(customer);
        when(accountService.createNewAccount("12345")).thenReturn(account);
        bankServiceFacade.openNewCurrentAccount(new OpenCurrentAccountDTO("12345", inputAmount));
        verify(transactionService, times(invokeTimes)).processTransaction("12345",
                "54321", BigDecimal.TEN,
                "Create account", "OK", APPROVED);
        verify(accountService, times(invokeTimes)).updateAccount(any());
        assertEquals(expectedAmount, account.getBalance());
    }

    @Test
    public void generateReportByCustomer() {
        String customerId = UUID.randomUUID().toString();
        Customer customer = new Customer(customerId, "John", "Smith", LocalDate.of(2001, 01, 01), true);
        when(customerService.validateCustomerAndFetch(customerId)).thenReturn(customer);
        Transaction transaction1 = Transaction.builder()
                .id("12345")
                .customerId(customerId)
                .operationType("Create Account")
                .amount(BigDecimal.ONE)
                .transactionTime(LocalDateTime.of(2001, 01, 01, 0, 0, 0))
                .sourceAccount("12345678")
                .ipAddress("127.0.0.1")
                .transactionStatus(APPROVED)
                .build();
        Transaction transaction2 = Transaction.builder()
                .id("54321")
                .customerId(customerId)
                .operationType("Create Account")
                .amount(BigDecimal.TEN)
                .transactionTime(LocalDateTime.of(2002, 01, 01, 0, 0, 0))
                .sourceAccount("87654321")
                .ipAddress("192.168.0.1")
                .transactionStatus(APPROVED)
                .build();
        mockReportGenerationLogic(customerId, List.of(transaction1, transaction2));
        final ReportDTO reportDTO = bankServiceFacade.generateReport(customerId);
        assertEquals(customerId, reportDTO.getCustomerId());
        assertEquals(BigDecimal.valueOf(11L), reportDTO.getBalance());
        assertEquals("John", reportDTO.getName());
        assertEquals("Smith", reportDTO.getSurname());
        assertEquals(true, reportDTO.isActive());
        assertEquals(2, reportDTO.getTransactions().size());
        assertEquals(convertToTransactionDTO(transaction1), reportDTO.getTransactions().get(0));
        assertEquals(convertToTransactionDTO(transaction2), reportDTO.getTransactions().get(1));
    }

    private void mockReportGenerationLogic(String customerId, List<Transaction> transactions) {
        when(accountService.fetchAllByCustomerId(customerId))
                .thenReturn(List.of(new Account(null, BigDecimal.ONE, customerId),
                        new Account(null, BigDecimal.TEN, customerId)));

        when(transactionService.fetchAllByCustomerId(customerId))
                .thenReturn(transactions);
    }

    @Test
    public void testGenerateReportAll() {
        String customerId1 = UUID.randomUUID().toString();
        final Customer customer1 = new Customer(customerId1, "John", "Smith", LocalDate.of(2001, 01, 01), true);
        String customerId2 = UUID.randomUUID().toString();
        final Customer customer2 = new Customer(customerId2, "Peter", "Pen", LocalDate.of(2001, 01, 01), true);
        when(customerService.fetchAllCustomers())
                .thenReturn(List.of(customer1, customer2));
        mockReportGenerationLogic("12345", Collections.emptyList());
        bankServiceFacade.generateReport();
        verify(accountService, times(2)).fetchAllByCustomerId(any());
        verify(transactionService, times(2)).fetchAllByCustomerId(any());
    }

    private TransactionDTO convertToTransactionDTO(Transaction t) {
        return new TransactionDTO(t.getId(), t.getOperationType(),
                t.getSourceAccount(), t.getAmount(), t.getTransactionTime(), t.getTransactionStatus().name());
    }

}