package com.test.bank.service.impl;

import com.test.bank.domain.model.Account;
import com.test.bank.repository.AccountRepository;
import com.test.bank.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/*******************************************************************************
 *
 * @author : <a href="mailto:borislepeshenkov@gmail.com">Boris Lepeshenkov</a>
 * @since : 14.03.2021
 */
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public Account createNewAccount(String customerId) {
        Account account = new Account();
        account.setId(UUID.randomUUID().toString());
        account.setCustomerId(customerId);
        return accountRepository.save(account);
    }

    @Override
    public Account updateAccount(Account account) {
        final Optional<Account> foundAccount = accountRepository.findById(account.getId());
        if (foundAccount.isEmpty()) {
            throw new IllegalArgumentException("Unknown account");
        }
        return accountRepository.save(account);
    }

    @Override
    public List<Account> fetchAllByCustomerId(String customerId) {
        return accountRepository.findAllByCustomerId(customerId);
    }
}