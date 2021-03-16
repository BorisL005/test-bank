package com.test.bank.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/*******************************************************************************
 *
 * @author : <a href="mailto:borislepeshenkov@gmail.com">Boris Lepeshenkov</a>
 * @since : 14.03.2021
 */
@Data
@AllArgsConstructor
public class AccountCreatedDTO {
    private String accountId;
    private BigDecimal amount;
}
