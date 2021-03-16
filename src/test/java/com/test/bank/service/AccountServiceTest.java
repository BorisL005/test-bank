package com.test.bank.service;

import com.test.bank.Application;
import com.test.bank.domain.model.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

/*******************************************************************************
 *
 * @author : <a href="mailto:borislepeshenkov@gmail.com">Boris Lepeshenkov</a>
 * @since : 14.03.2021
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Test
    public void createNewAccount() {
        final String customerId = UUID.randomUUID().toString();
        final Account newAccount = accountService.createNewAccount(customerId);
        final List<Account> accounts = accountService.fetchAllByCustomerId(customerId);
        assertEquals(1, accounts.size());
        assertEquals(newAccount, accounts.get(0));
    }

    @Test
    public void updateAccount() {
        final String customerId = UUID.randomUUID().toString();
        final Account newAccount = accountService.createNewAccount(customerId);
        newAccount.setBalance(BigDecimal.TEN);
        accountService.updateAccount(newAccount);
        final List<Account> accounts = accountService.fetchAllByCustomerId(customerId);
        assertEquals(BigDecimal.TEN, accounts.get(0).getBalance());
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateAccountFailed() {
        final String customerId = UUID.randomUUID().toString();
        final Account newAccount = accountService.createNewAccount(customerId);
        newAccount.setBalance(BigDecimal.TEN);
        accountService.updateAccount(new Account());
    }

    @Test
    public void fetchAllByCustomerId() {
        final String customerId = UUID.randomUUID().toString();
        accountService.createNewAccount(customerId);
        accountService.createNewAccount(customerId);
        accountService.createNewAccount(customerId);
        final List<Account> accounts = accountService.fetchAllByCustomerId(customerId);
        assertEquals(3, accounts.size());
    }
}