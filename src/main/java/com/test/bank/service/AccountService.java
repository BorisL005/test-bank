package com.test.bank.service;

import com.test.bank.domain.model.Account;

import java.util.List;

/*******************************************************************************
 *
 * @author : <a href="mailto:borislepeshenkov@gmail.com">Boris Lepeshenkov</a>
 * @since : 14.03.2021
 */
public interface AccountService {
    Account createNewAccount(String customerId);
    Account updateAccount(Account account);
    List<Account> fetchAllByCustomerId(String customerId);
}
