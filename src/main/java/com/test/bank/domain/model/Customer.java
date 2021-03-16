package com.test.bank.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

import java.time.LocalDate;

/*******************************************************************************
 *
 * @author : <a href="mailto:borislepeshenkov@gmail.com">Boris Lepeshenkov</a>
 * @since : 14.03.2021
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@KeySpace("customers")
public class Customer {
    @Id
    private String id;
    private String name;
    private String surname;
    private LocalDate dob;
    private boolean isActive;
}
