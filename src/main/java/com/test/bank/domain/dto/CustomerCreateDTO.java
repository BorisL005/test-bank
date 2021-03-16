package com.test.bank.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/*******************************************************************************
 *
 * @author : <a href="mailto:borislepeshenkov@gmail.com">Boris Lepeshenkov</a>
 * @since : 16.03.2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerCreateDTO {
    private String name;
    private String surname;
    private LocalDate dob;
    private boolean isActive;
}
