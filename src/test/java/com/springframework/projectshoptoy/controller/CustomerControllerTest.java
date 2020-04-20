package com.springframework.projectshoptoy.controller;

import com.springframework.projectshoptoy.domain.Account;
import com.springframework.projectshoptoy.domain.Customer;
import com.springframework.projectshoptoy.service.AccountService;
import com.springframework.projectshoptoy.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class CustomerControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Mock
    CustomerService customerService;

    CustomerController customerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        customerController=new CustomerController(customerService);

        mockMvc= MockMvcBuilders.standaloneSetup(customerController)
                .build();
    }

    @Test
    void accounts() throws  Exception{
        Set<Customer> customers=new HashSet<>();
        Customer customer=new Customer();
//        customer.setAccount(account);
        customer.setAddress("33/16");
        customer.setCity("usa");
        customer.setEmail("employee@gmail.com");
        customer.setFirstName("kim");
        customer.setLastName("quyen");

        customers.add(customer);
        when(customerService.getCustomers()).thenReturn(customers);

        mockMvc.perform(get(CustomerController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteCustomerTrue() throws  Exception{
        when(customerService.deleteCustomerById(any())).thenReturn(true);
        mockMvc.perform(delete(CustomerController.BASE_URL+"/1"))
                .andExpect(status().isOk());
//                .andExpect(view().name("redirect:/"));
    }

    @Test
    public void deleteCustomerFalse() throws  Exception{
        when(customerService.deleteCustomerById(any())).thenReturn(false);
        mockMvc.perform(delete(CustomerController.BASE_URL+"/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findCustomerByUserName() throws Exception{
        mockMvc.perform(get(CustomerController.BASE_URL+"/userName/1"))
                .andExpect(status().isOk());
    }




}