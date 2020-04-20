package com.springframework.projectshoptoy.service;

import com.springframework.projectshoptoy.domain.Account;
import com.springframework.projectshoptoy.repositories.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
@Slf4j
@Service
public class AccountServiceImpl implements  AccountService{
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Set<Account> getListAccount() {
        log.debug("get list accounts");
        Set<Account> accountSet=new HashSet<>();
        accountRepository.findAll().iterator().forEachRemaining(accountSet::add);
        return accountSet;
    }
}
