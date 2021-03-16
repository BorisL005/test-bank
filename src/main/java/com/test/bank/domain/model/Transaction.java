package com.test.bank.domain.model;

import com.test.bank.domain.model.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

import java.math.BigDecimal;
import java.time.LocalDateTime;


/*******************************************************************************
 *
 * @author : <a href="mailto:borislepeshenkov@gmail.com">Boris Lepeshenkov</a>
 * @since : 14.03.2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@KeySpace("transactions")
public class Transaction {
    @Id
    private String id;
    private String operationType;
    private String customerId;
    private String sourceAccount;
    private String ipAddress;
    private String message;
    private BigDecimal amount;
    private LocalDateTime transactionTime;
    private TransactionStatus transactionStatus;
}
