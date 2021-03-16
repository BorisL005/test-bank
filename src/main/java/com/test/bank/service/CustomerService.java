package com.test.bank.service;

import com.test.bank.domain.dto.CustomerDTO;
import com.test.bank.domain.model.Customer;

import java.util.List;

/*******************************************************************************
 *
 * @author : <a href="mailto:borislepeshenkov@gmail.com">Boris Lepeshenkov</a>
 * @since : 14.03.2021
 */
public interface CustomerService {

    CustomerDTO createCustomer(CustomerDTO customer);

    CustomerDTO updateCustomer(CustomerDTO customer);

    void deleteCustomer(String customerId);

    List<Customer> fetchAllCustomers();

    List<CustomerDTO> fetchAllCustomersAndConvert();

    CustomerDTO fetchCustomer(String customerId);

    Customer validateCustomerAndFetch(String customerId);
}
