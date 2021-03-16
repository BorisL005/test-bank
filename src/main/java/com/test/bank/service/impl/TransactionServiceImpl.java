package com.test.bank.service.impl;

import com.test.bank.domain.model.Transaction;
import com.test.bank.domain.model.enums.TransactionStatus;
import com.test.bank.repository.TransactionRepository;
import com.test.bank.service.TransactionService;
import com.test.bank.utils.IpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/*******************************************************************************
 *
 * @author : <a href="mailto:borislepeshenkov@gmail.com">Boris Lepeshenkov</a>
 * @since : 14.03.2021
 */
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final HttpServletRequest httpServletRequest;

    @Override
    public void processTransaction(String customerId, String accountId, BigDecimal amount, String operation,
                                   String message, TransactionStatus transactionStatus) {
        Transaction transaction = Transaction
                .builder()
                .id(UUID.randomUUID().toString())
                .customerId(customerId)
                .sourceAccount(accountId)
                .amount(amount)
                .operationType(operation)
                .message(message)
                .transactionStatus(transactionStatus)
                .transactionTime(LocalDateTime.now())
                .ipAddress(IpUtils.getClientIpAddr(httpServletRequest))
                .build();
        transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> fetchAllByCustomerId(String customerId) {
        return transactionRepository.findAllByCustomerIdOrderByTransactionTimeDesc(customerId);
    }
}
