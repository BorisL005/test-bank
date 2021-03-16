package com.test.bank.controller;

import com.test.bank.Application;
import com.test.bank.domain.dto.CustomerDTO;
import com.test.bank.utils.TestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*******************************************************************************
 *
 * @author : <a href="mailto:borislepeshenkov@gmail.com">Boris Lepeshenkov</a>
 * @since : 16.03.2021
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
@AutoConfigureMockMvc
public class CustomerControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private TestUtils testUtils;

    @Test
    public void getCustomerNotFound() throws Exception {
        mvc.perform(get("/management/customers/" + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    public void getCustomerOK() throws Exception {
        final CustomerDTO customer = testUtils.createCustomer("John", "Smith", LocalDate.of(2001, 01, 01));
        mvc.perform(get("/management/customers/" + customer.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.surname").value("Smith"))
                .andExpect(jsonPath("$.dob").exists())
                .andExpect(jsonPath("$.active").value(true));
    }

    @Test
    public void getCustomers() throws Exception {
        testUtils.createCustomer("John", "Smith", LocalDate.of(2001, 01, 01));
        testUtils.createCustomer("Peter", "Pen", LocalDate.of(2002, 02, 02));
        mvc.perform(get("/management/customers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void createCustomer() throws Exception {
        mvc.perform(post("/management/customers").content("{\n" +
                "    \"name\": \"Ivan\",\n" +
                "    \"surname\": \"Ivanov\",\n" +
                "    \"dob\": \"2020-01-20\",\n" +
                "    \"active\": true\n" +
                "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Ivan"))
                .andExpect(jsonPath("$.surname").value("Ivanov"))
                .andExpect(jsonPath("$.dob").exists())
                .andExpect(jsonPath("$.active").value(true));
    }

    @Test
    public void updateCustomer() throws Exception {
        final CustomerDTO customer = testUtils.createCustomer("John", "Smith", LocalDate.of(2002, 02, 02));
        mvc.perform(put("/management/customers").content("{\n" +
                "    \"id\": \"" + customer.getId() + "\",\n" +
                "    \"name\": \"Ivan\",\n" +
                "    \"surname\": \"Ivanov\",\n" +
                "    \"dob\": \"2020-01-20\",\n" +
                "    \"active\": false\n" +
                "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Ivan"))
                .andExpect(jsonPath("$.surname").value("Ivanov"))
                .andExpect(jsonPath("$.dob").exists())
                .andExpect(jsonPath("$.active").value(false));

    }

    @Test
    public void updateCustomerFailed() throws Exception {
        final CustomerDTO customer = testUtils.createCustomer("John", "Smith", LocalDate.of(2002, 02, 02));
        mvc.perform(put("/management/customers").content("{\n" +
                "    \"name\": \"Ivan\",\n" +
                "    \"surname\": \"Ivanov\",\n" +
                "    \"dob\": \"2020-01-20\",\n" +
                "    \"active\": false\n" +
                "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

    }

    @Test
    public void deleteCustomer() throws Exception {
        final CustomerDTO customer = testUtils.createCustomer("John", "Smith", LocalDate.of(2001, 01, 01));
        mvc.perform(delete("/management/customers/" + customer.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()));

    }
}