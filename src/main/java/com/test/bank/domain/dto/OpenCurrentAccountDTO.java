package com.test.bank.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/*******************************************************************************
 *
 * @author : <a href="mailto:borislepeshenkov@gmail.com">Boris Lepeshenkov</a>
 * @since : 14.03.2021
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenCurrentAccountDTO {
    private String customerId;
    private BigDecimal initialCredit;
}
