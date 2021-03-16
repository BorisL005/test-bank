package com.test.bank.controller;

import com.test.bank.domain.dto.AccountCreatedDTO;
import com.test.bank.domain.dto.OpenCurrentAccountDTO;
import com.test.bank.domain.dto.ReportDTO;
import com.test.bank.service.BankServiceFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/*******************************************************************************
 *
 * @author : <a href="mailto:borislepeshenkov@gmail.com">Boris Lepeshenkov</a>
 * @since : 14.03.2021
 */
@RestController
@RequiredArgsConstructor
public class BankController {

    private final BankServiceFacade bankServiceFacade;

    @ApiOperation(value = "Creates an Account, attaches it to provided customer")
    @PostMapping(path = "/bank/customers/add-account", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "", response = AccountCreatedDTO.class),
    })
    public ResponseEntity<Object> createNewAccount(@RequestBody OpenCurrentAccountDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bankServiceFacade.openNewCurrentAccount(dto));
    }

    @ApiOperation(value = "Generates report by all existing customers")
    @GetMapping(path = "/bank/customers", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "", responseContainer = "List", response = ReportDTO.class),
    })
    public ResponseEntity<Object> generateReport() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bankServiceFacade.generateReport());
    }

    @ApiOperation(value = "Generates report by particular customer")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "", response = ReportDTO.class),
    })
    @GetMapping(path = "/bank/customers/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> generateReport(
            @PathVariable("customerId") String customerId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bankServiceFacade.generateReport(customerId));
    }

}
