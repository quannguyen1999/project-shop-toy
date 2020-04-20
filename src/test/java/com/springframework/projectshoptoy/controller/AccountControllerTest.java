package com.springframework.projectshoptoy.controller;

import com.springframework.projectshoptoy.domain.Account;
import com.springframework.projectshoptoy.exception.ConflixIdException;
import com.springframework.projectshoptoy.repositories.AccountRepository;
import com.springframework.projectshoptoy.service.AccountService;
import com.springframework.projectshoptoy.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class AccountControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Mock
    AccountService accountService;



    AccountController accountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        accountController=new AccountController(accountService);

        mockMvc= MockMvcBuilders.standaloneSetup(accountController)
                .build();
    }

    //lấy danh sách account
    @Test
    void accounts() throws  Exception{
        Set<Account> accounts=new HashSet<>();
        Account account=new Account();
        account.setUserName("admin");
        account.setPassword("fuck");

        Account account1=new Account();
        account1.setUserName("employee");
        account1.setPassword("123");

        accounts.add(account);
        accounts.add(account1);

        when(accountService.getListAccount()).thenReturn(accounts);

        mockMvc.perform(get(AccountController.BASE_URL)
               .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    //kiểm tra xoa account
    @Test
    public void deleteAccount() throws  Exception{
        when(accountService.deleteAccount(any())).thenReturn(true);
        mockMvc.perform(delete(AccountController.BASE_URL+"/1"))
                .andExpect(status().isOk());
//                .andExpect(view().name("redirect:/"));
    }

    //tìm kiếm account trả về exception
    @Test
    public void findAccountByIdException() throws  Exception{
        when(accountService.findAccountByUserName(anyString())).thenReturn(null);

        mockMvc.perform(delete(AccountController.BASE_URL+"/1"))
                .andExpect(status().isNotFound());
    }

    //tìm kiếm account không trả về exception
    public void findAccountByIdNoException() throws  Exception{
        Account account=new Account();
        account.setUserName("admin");
        account.setPassword("fuck");
        when(accountService.findAccountByUserName(anyString())).thenReturn(account);
        mockMvc.perform(delete(AccountController.BASE_URL+"/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void createNewAccount() throws  Exception{
        Account account=new Account();
        account.setUserName("admin");
        account.setPassword("123");

        when(accountService.createNewAccount(account)).thenThrow(new ConflixIdException());

        mockMvc.perform(post(AccountController.BASE_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(AbstractRestControllerTest.asJsonString(account)))
                .andExpect(status().isConflict());


    }
}