package com.test.bank.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/*******************************************************************************
 *
 * @author : <a href="mailto:borislepeshenkov@gmail.com">Boris Lepeshenkov</a>
 * @since : 14.03.2021
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {
    private String customerId;
    private String name;
    private String surname;
    private BigDecimal balance;
    private boolean active;
    private List<TransactionDTO> transactions;
}
