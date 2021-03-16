package com.test.bank.controller;

import com.test.bank.Application;
import com.test.bank.domain.dto.CustomerDTO;
import com.test.bank.domain.dto.OpenCurrentAccountDTO;
import com.test.bank.service.BankServiceFacade;
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

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
public class BankControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private TestUtils testUtils;
    @Autowired
    private BankServiceFacade bankServiceFacade;

    @Test
    public void createNewAccount() throws Exception {
        final CustomerDTO customer = testUtils.createCustomer("John", "Smith", LocalDate.of(2001, 01, 01));
        mvc.perform(post("/bank/customers/add-account").content("{\n" +
                "    \"customerId\": \"" + customer.getId() + "\",\n" +
                "    \"initialCredit\" : \"10000\"\n" +
                "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accountId").exists())
                .andExpect(jsonPath("$.amount").value(10000));
    }

    @Test
    public void generateReport() throws Exception {
        final CustomerDTO customer = testUtils.createCustomer("John", "Smith", LocalDate.of(2001, 01, 01));
        final OpenCurrentAccountDTO openCurrentAccountDTO = new OpenCurrentAccountDTO(customer.getId(), BigDecimal.valueOf(135.55));
        bankServiceFacade.openNewCurrentAccount(openCurrentAccountDTO);
        mvc.perform(get("/bank/customers/" + customer.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.customerId").exists())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.surname").value("Smith"))
                .andExpect(jsonPath("$.balance").value(135.55))
                .andExpect(jsonPath("$.active").value(true))
                .andExpect(jsonPath("$.transactions").exists())
                .andExpect(jsonPath("$.transactions[0].id").exists())
                .andExpect(jsonPath("$.transactions[0].operationType").value("Create account"))
                .andExpect(jsonPath("$.transactions[0].sourceAccount").exists())
                .andExpect(jsonPath("$.transactions[0].amount").value(135.55))
                .andExpect(jsonPath("$.transactions[0].transactionTime").exists())
                .andExpect(jsonPath("$.transactions[0].transactionStatus").value("APPROVED"));
    }

    @Test
    public void testGenerateReport() throws Exception {
        final CustomerDTO customer1 = testUtils.createCustomer("John", "Smith", LocalDate.of(2001, 01, 01));
        final CustomerDTO customer2 = testUtils.createCustomer("Peter", "Pen", LocalDate.of(2001, 01, 01));
        final OpenCurrentAccountDTO openCurrentAccountDTO1 = new OpenCurrentAccountDTO(customer1.getId(), BigDecimal.valueOf(135.55));
        final OpenCurrentAccountDTO openCurrentAccountDTO2 = new OpenCurrentAccountDTO(customer2.getId(), BigDecimal.TEN);
        bankServiceFacade.openNewCurrentAccount(openCurrentAccountDTO1);
        bankServiceFacade.openNewCurrentAccount(openCurrentAccountDTO2);
        mvc.perform(get("/bank/customers/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

}