package com.test.bank.utils;

import com.test.bank.domain.dto.CustomerDTO;
import com.test.bank.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/*******************************************************************************
 *
 * @author : <a href="mailto:borislepeshenkov@gmail.com">Boris Lepeshenkov</a>
 * @since : 16.03.2021
 */
@Component
@RequiredArgsConstructor
public class TestUtils {

    private final CustomerService customerService;

    public CustomerDTO createCustomer(String name, String surname, LocalDate dob) {
        return customerService.createCustomer(new CustomerDTO(null, name, surname, dob, true));
    }
}
