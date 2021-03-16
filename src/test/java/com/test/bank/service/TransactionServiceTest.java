package com.test.bank.service;

import com.test.bank.Application;
import com.test.bank.domain.model.Transaction;
import com.test.bank.domain.model.enums.TransactionStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static com.test.bank.domain.model.enums.TransactionStatus.APPROVED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/*******************************************************************************
 *
 * @author : <a href="mailto:borislepeshenkov@gmail.com">Boris Lepeshenkov</a>
 * @since : 16.03.2021
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @Test
    public void processTransaction() {
        String customerId = UUID.randomUUID().toString();
        String accountId = UUID.randomUUID().toString();
        BigDecimal amount = BigDecimal.TEN;
        String operation = "Sale";
        String message = "OK";
        TransactionStatus status = APPROVED;
        HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.when(httpServletRequest.getHeader("X-Forwarded-For")).thenReturn("127.0.0.1");
        transactionService.processTransaction(customerId, accountId, amount, operation, message, status);
        final List<Transaction> transactions = transactionService.fetchAllByCustomerId(customerId);
        assertEquals(1, transactions.size());
        final Transaction transaction = transactions.get(0);
        assertNotNull(transaction.getId());
        assertEquals(customerId, transaction.getCustomerId());
        assertEquals(accountId, transaction.getSourceAccount());
        assertEquals(amount, transaction.getAmount());
        assertEquals(operation, transaction.getOperationType());
        assertEquals(message, transaction.getMessage());
        assertEquals("127.0.0.1", transaction.getIpAddress());
        assertNotNull(transaction.getTransactionTime());
    }

    @Test
    public void fetchAllByCustomerId() {
        String customerId = UUID.randomUUID().toString();
        transactionService.processTransaction(customerId, "Account", BigDecimal.ZERO, "OP","OK", APPROVED);
        final List<Transaction> transactions = transactionService.fetchAllByCustomerId(customerId);
        assertEquals(1, transactions.size());
    }

}