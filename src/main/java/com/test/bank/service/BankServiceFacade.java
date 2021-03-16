package com.test.bank.service;

import com.test.bank.domain.dto.AccountCreatedDTO;
import com.test.bank.domain.dto.OpenCurrentAccountDTO;
import com.test.bank.domain.dto.ReportDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/*******************************************************************************
 *
 * @author : <a href="mailto:borislepeshenkov@gmail.com">Boris Lepeshenkov</a>
 * @since : 14.03.2021
 */
@Service
public interface BankServiceFacade {

    AccountCreatedDTO openNewCurrentAccount(OpenCurrentAccountDTO dto);

    ReportDTO generateReport(String customerId);

    List<ReportDTO> generateReport();
}
