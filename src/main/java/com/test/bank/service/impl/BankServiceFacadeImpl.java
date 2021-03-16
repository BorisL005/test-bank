package com.test.bank.service.impl;

import com.test.bank.domain.dto.AccountCreatedDTO;
import com.test.bank.domain.dto.OpenCurrentAccountDTO;
import com.test.bank.domain.dto.ReportDTO;
import com.test.bank.domain.dto.TransactionDTO;
import com.test.bank.domain.model.Account;
import com.test.bank.domain.model.Customer;
import com.test.bank.domain.model.Transaction;
import com.test.bank.domain.model.enums.TransactionStatus;
import com.test.bank.service.AccountService;
import com.test.bank.service.BankServiceFacade;
import com.test.bank.service.CustomerService;
import com.test.bank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/*******************************************************************************
 *
 * @author : <a href="mailto:borislepeshenkov@gmail.com">Boris Lepeshenkov</a>
 * @since : 14.03.2021
 */
@Service
@RequiredArgsConstructor
public class BankServiceFacadeImpl implements BankServiceFacade {
    private final AccountService accountService;
    private final CustomerService customerService;
    private final TransactionService transactionService;

    /**
     * This service must be Transactional, however, for test purposes I have not set up
     * any transactional managers and due to this @Transactional annotaion will have no effect here.
     */
    @Override
    public AccountCreatedDTO openNewCurrentAccount(OpenCurrentAccountDTO dto) {
        final String customerId = dto.getCustomerId();
        final BigDecimal initialCredit = dto.getInitialCredit();
        customerService.validateCustomerAndFetch(customerId);
        final Account newAccount = accountService.createNewAccount(customerId);
        if (initialCredit != null && initialCredit.compareTo(BigDecimal.ZERO) > 0) {
            transactionService.processTransaction(customerId,
                    newAccount.getId(), initialCredit,
                    "Create account", "OK", TransactionStatus.APPROVED);
            newAccount.setBalance(initialCredit);
            accountService.updateAccount(newAccount);
        }
        return new AccountCreatedDTO(newAccount.getId(), initialCredit);
    }

    @Override
    public ReportDTO generateReport(String customerId) {
        final Customer customer = customerService.validateCustomerAndFetch(customerId);
        return generateReport(customer);
    }

    @Override
    public List<ReportDTO> generateReport() {
        return customerService
                .fetchAllCustomers()
                .stream()
                .map(this::generateReport)
                .collect(Collectors.toList());
    }

    private ReportDTO generateReport(Customer customer) {
        String customerId = customer.getId();
        BigDecimal customerBalance = accountService
                .fetchAllByCustomerId(customerId)
                .stream()
                .map(Account::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        final List<Transaction> transactions = transactionService.fetchAllByCustomerId(customerId);
        List<TransactionDTO> transactionsDTO = transactions.stream()
                .map(t -> new TransactionDTO(t.getId(), t.getOperationType(),
                        t.getSourceAccount(), t.getAmount(), t.getTransactionTime(), t.getTransactionStatus().name()))
                .collect(Collectors.toList());
        return new ReportDTO(customer.getId(), customer.getName(),
                customer.getSurname(), customerBalance,
                customer.isActive(), transactionsDTO);
    }

}
