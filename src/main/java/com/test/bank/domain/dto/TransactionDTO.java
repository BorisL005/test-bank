package com.test.bank.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/*******************************************************************************
 *
 * @author : <a href="mailto:borislepeshenkov@gmail.com">Boris Lepeshenkov</a>
 * @since : 14.03.2021
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    @Id
    private String id;
    private String operationType;
    private String sourceAccount;
    private BigDecimal amount;
    private LocalDateTime transactionTime;
    private String transactionStatus;
}
