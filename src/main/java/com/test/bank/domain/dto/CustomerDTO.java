package com.test.bank.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/*******************************************************************************
 *
 * @author : <a href="mailto:borislepeshenkov@gmail.com">Boris Lepeshenkov</a>
 * @since : 14.03.2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private String id;
    private String name;
    private String surname;
    private LocalDate dob;
    private boolean isActive;

    public CustomerDTO(CustomerCreateDTO dto) {
        this.name = dto.getName();
        this.surname = dto.getSurname();
        this.dob = dto.getDob();
        this.isActive = dto.isActive();
    }
}
