package com.test.bank.service;

import com.test.bank.domain.model.Transaction;
import com.test.bank.domain.model.enums.TransactionStatus;

import java.math.BigDecimal;
import java.util.List;

/*******************************************************************************
 *
 * @author : <a href="mailto:borislepeshenkov@gmail.com">Boris Lepeshenkov</a>
 * @since : 14.03.2021
 */
public interface TransactionService {

    void processTransaction(String customerId, String accountId, BigDecimal amount, String operation,
                            String message, TransactionStatus transactionStatus);

    List<Transaction> fetchAllByCustomerId(String customerId);
}
