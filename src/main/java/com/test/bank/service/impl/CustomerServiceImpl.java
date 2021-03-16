package com.test.bank.service.impl;

import com.test.bank.domain.dto.CustomerDTO;
import com.test.bank.domain.model.Customer;
import com.test.bank.exceptions.CustomerInactiveException;
import com.test.bank.exceptions.CustomerNotFoundException;
import com.test.bank.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/*******************************************************************************
 *
 * @author : <a href="mailto:borislepeshenkov@gmail.com">Boris Lepeshenkov</a>
 * @since : 14.03.2021
 */
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements com.test.bank.service.CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public CustomerDTO createCustomer(CustomerDTO customer) {
        Customer customerPO = convertCustomerDTO2CustomerPO(customer);
        customerPO.setId(UUID.randomUUID().toString());
        return convertCustomerPO2CustomerDTO(customerRepository.save(customerPO));
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customer) {
        if (customer.getId() == null) {
            throw new IllegalArgumentException("CustomerId is required");
        }
        Customer customerPO = convertCustomerDTO2CustomerPO(customer);
        customerPO.setId(customer.getId());
        return convertCustomerPO2CustomerDTO(customerRepository.save(customerPO));
    }

    @Override
    public void deleteCustomer(String customerId) {
        customerRepository.findById(customerId)
                .ifPresent(customerRepository::delete);
    }

    @Override
    public List<CustomerDTO> fetchAllCustomersAndConvert() {
        return customerRepository
                .findAll()
                .stream()
                .map(this::convertCustomerPO2CustomerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO fetchCustomer(String customerId) {
        return customerRepository
                .findById(customerId)
                .map(this::convertCustomerPO2CustomerDTO)
                .orElse(null);
    }

    @Override
    public List<Customer> fetchAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer validateCustomerAndFetch(String customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty()) {
            throw new CustomerNotFoundException(customerId);
        }
        if (!customer.get().isActive()) {
            throw new CustomerInactiveException(customerId);
        }
        return customer.get();
    }

    private Customer convertCustomerDTO2CustomerPO(CustomerDTO customer) {
        Customer customerPO = new Customer();
        customerPO.setActive(customer.isActive());
        customerPO.setDob(customer.getDob());
        customerPO.setName(customer.getName());
        customerPO.setSurname(customer.getSurname());
        return customerPO;
    }

    private CustomerDTO convertCustomerPO2CustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setActive(customer.isActive());
        customerDTO.setDob(customer.getDob());
        customerDTO.setName(customer.getName());
        customerDTO.setSurname(customer.getSurname());
        return customerDTO;
    }

}
