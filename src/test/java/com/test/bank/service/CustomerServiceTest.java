package com.test.bank.service;

import com.test.bank.Application;
import com.test.bank.domain.dto.CustomerDTO;
import com.test.bank.domain.model.Customer;
import com.test.bank.exceptions.CustomerInactiveException;
import com.test.bank.exceptions.CustomerNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/*******************************************************************************
 *
 * @author : <a href="mailto:borislepeshenkov@gmail.com">Boris Lepeshenkov</a>
 * @since : 16.03.2021
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;

    @Test
    public void createCustomer() {
        CustomerDTO created = doCreateCustomer();
        assertNotNull(created.getId());
        assertEquals("John", created.getName());
        assertEquals("Smith", created.getSurname());
        assertEquals(LocalDate.of(2001, 01, 01), created.getDob());
        assertEquals(true, created.isActive());
    }

    @Test
    public void updateCustomer() {
        final CustomerDTO created = doCreateCustomer();
        created.setActive(false);
        created.setName("Peter");
        created.setSurname("Pen");
        created.setDob(LocalDate.of(2002, 02, 02));
        customerService.updateCustomer(created);
        customerService.fetchCustomer(created.getId());
        assertEquals("Peter", created.getName());
        assertEquals("Pen", created.getSurname());
        assertEquals(LocalDate.of(2002, 02, 02), created.getDob());
        assertEquals(false, created.isActive());
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateCustomerFailed() {
        final CustomerDTO created = doCreateCustomer();
        created.setId(null);
        customerService.updateCustomer(created);
    }

    @Test
    public void deleteCustomer() {
        final CustomerDTO customerDTO = doCreateCustomer();
        customerService.deleteCustomer(customerDTO.getId());
        assertNull(customerService.fetchCustomer(customerDTO.getId()));
    }

    @Test
    public void fetchAllCustomers() {
        final CustomerDTO customerDTO1 = doCreateCustomer();
        final CustomerDTO customerDTO2 = doCreateCustomer();
        final Set<String> all = customerService.fetchAllCustomers()
                .stream().map(Customer::getId).collect(Collectors.toSet());
        assertTrue(all.contains(customerDTO1.getId()));
        assertTrue(all.contains(customerDTO2.getId()));
    }

    @Test
    public void fetchAllCustomersAndConvert() {
        final CustomerDTO customerDTO1 = doCreateCustomer();
        final Map<String, CustomerDTO> allConverted = customerService
                .fetchAllCustomersAndConvert()
                .stream()
                .collect(Collectors.toMap(CustomerDTO::getId, c -> c));
        assertEquals(customerDTO1, allConverted.get(customerDTO1.getId()));

    }

    @Test
    public void fetchCustomer() {
        final CustomerDTO expected = doCreateCustomer();
        final CustomerDTO actual = customerService.fetchCustomer(expected.getId());
        assertEquals(expected, actual);
    }

    @Test(expected = CustomerInactiveException.class)
    public void validateCustomerAndFetchInActive() {
        final CustomerDTO customerDTO = doCreateCustomer();
        customerDTO.setActive(false);
        customerService.updateCustomer(customerDTO);
        customerService.validateCustomerAndFetch(customerDTO.getId());
    }

    @Test(expected = CustomerNotFoundException.class)
    public void validateCustomerAndFetchNotFound() {
        customerService.validateCustomerAndFetch(UUID.randomUUID().toString());
    }

    @Test
    public void validateCustomerAndFetch() {
        final CustomerDTO customerDTO = doCreateCustomer();
        final Customer customer = customerService.validateCustomerAndFetch(customerDTO.getId());
        assertNotNull(customer);
    }

    private CustomerDTO doCreateCustomer() {
        final CustomerDTO expected = new CustomerDTO();
        expected.setName("John");
        expected.setSurname("Smith");
        expected.setDob(LocalDate.of(2001, 01, 01));
        expected.setActive(true);
        return customerService.createCustomer(expected);
    }

}