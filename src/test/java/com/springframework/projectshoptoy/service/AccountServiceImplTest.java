package com.springframework.projectshoptoy.service;

import com.springframework.projectshoptoy.domain.Account;
import com.springframework.projectshoptoy.repositories.AccountRepository;
import com.springframework.projectshoptoy.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountServiceImplTest {
    AccountServiceImpl accountServiceImpl;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    AccountRepository accountRepository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        accountServiceImpl=new AccountServiceImpl(accountRepository,customerRepository);
    }

    //kiểm tra danh sách account
    @Test
    void getAccount() {
        Set<Account> accounts=new HashSet<>();
        Account account=new Account();
        account.setUserName("admin");
        account.setPassword("fuck");


        Account account1=new Account();
        account1.setUserName("employee");
        account1.setPassword("123");

        accounts.add(account);
        accounts.add(account1);

        when(accountRepository.findAll()).thenReturn(accounts);

        Set<Account> resultListAccount=accountServiceImpl.getListAccount();

        assertEquals(2,resultListAccount.size());
        verify(accountRepository,times(1)).findAll();
    }

    //lấy account bằng username
    @Test
    void findAccountByUserName(){
        Account account=new Account();
        account.setUserName("admin");
        when(accountRepository.findById(anyString())).thenReturn(java.util.Optional.of(account));

        Account accountFind=accountServiceImpl.findAccountByUserName("admin");

        assertEquals("admin",accountFind.getUserName());
    }

//    //kiểm tra not found exception
//    @Test
//    void testNotFoundException(){
//        Account account=new Account();
//        account.setUserName("admin");
//
//        when(accountRepository.findById(any())).then(null);
//
//        Account accountFind=accountServiceImpl.findAccountByUserName("admin");
//    }
}