package com.test.bank.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*******************************************************************************
 *
 * @author : <a href="mailto:borislepeshenkov@gmail.com">Boris Lepeshenkov</a>
 * @since : 14.03.2021
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDTO {
    private int errorCode;
    private String errorMessage;
}
