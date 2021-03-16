package com.test.bank.controller;

import com.test.bank.domain.dto.CustomerCreateDTO;
import com.test.bank.domain.dto.CustomerDTO;
import com.test.bank.service.CustomerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

/*******************************************************************************
 *
 * @author : <a href="mailto:borislepeshenkov@gmail.com">Boris Lepeshenkov</a>
 * @since : 14.03.2021
 */
@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping(path = "/management/customers/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Retrieves customer by provided customerID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = CustomerDTO.class),
    })
    public ResponseEntity<Object> getCustomer(
            @ApiParam(name = "customer id", example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable("id") String id) {
        final CustomerDTO customerDTO = customerService.fetchCustomer(id);
        if (customerDTO == null) {
            return ResponseEntity.status(NOT_FOUND).build();
        }
        return ResponseEntity.status(OK).body(customerDTO);
    }

    @ApiOperation(value = "Retrieves all existing customers in the system")
    @GetMapping(path = "/management/customers", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", responseContainer = "List", response = CustomerDTO.class),
    })
    public ResponseEntity<Object> getCustomers() {
        return ResponseEntity.status(OK).body(customerService.fetchAllCustomersAndConvert());
    }

    @ApiOperation(value = "Creates new customer")
    @PostMapping(path = "/management/customers", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "", response = CustomerDTO.class),
    })
    public ResponseEntity<Object> createCustomer(
            @RequestBody CustomerCreateDTO customerDTO) {
        return ResponseEntity.status(CREATED).body(customerService.createCustomer(new CustomerDTO(customerDTO)));
    }

    @ApiOperation(value = "Updates existing customer")
    @PutMapping(path = "/management/customers", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = CustomerDTO.class),
    })
    public ResponseEntity<Object> updateCustomer(@RequestBody CustomerDTO customerDTO) {
        if (customerDTO.getId() == null) {
            return ResponseEntity.status(BAD_REQUEST).body("Id field is required");
        }
        return ResponseEntity.status(OK).body(customerService.updateCustomer(customerDTO));
    }

    @ApiOperation(value = "Deletes existing customer")
    @DeleteMapping(path = "/management/customers/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = ""),
    })
    public ResponseEntity<Object> deleteCustomer(@PathVariable("id") String id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok().build();
    }

}
