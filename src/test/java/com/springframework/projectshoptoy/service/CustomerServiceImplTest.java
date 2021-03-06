package com.springframework.projectshoptoy.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.springframework.projectshoptoy.api.domain.Account;
import com.springframework.projectshoptoy.api.domain.Customer;
import com.springframework.projectshoptoy.api.domain.Order;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerServiceImplTest {
    CustomerServiceImpl customerServiceImpl;

//    @Mock
//    CustomerRepository customerRepository;
//
//    @Mock
//    AccountRepository accountRepository;
//    
//    @Mock
//    OrderRepository orderRepository;
    


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getCustomers() {
//        Set<Customer> customerSet=new HashSet<>();
//        Customer customer=new Customer();
////        customer.setAccount(account);
//        customer.setAddress("33/16");
//        customer.setCity("usa");
//        customer.setEmail("employee@gmail.com");
//        customer.setFirstName("kim");
//        customer.setLastName("quyen");
//
//        customerSet.add(customer);
//
//        when(customerRepository.findAll()).thenReturn(customerSet);
//
//        Set<Customer> resultListCustomer=customerServiceImpl.getCustomers();
//
//        assertEquals(1,resultListCustomer.size());
//        verify(customerRepository,times(1)).findAll();
    }

    @Test
    void findCustomerByUserName() throws Exception {
//        Account account=new Account();
//        account.setUserName("admin");
//
//        Customer customerFind=new Customer();
//        customerFind.setFirstName("quan");
//        customerFind.setAccount(account);
//
//        when(customerRepository.findCustomerByUserName(any())).thenReturn(customerFind);
//
//        Customer customer=customerServiceImpl.findCustomerByUserName(account.getUserName());
//
//        assertEquals(account.getUserName(),customer.getAccount().getUserName());

    }
    
    @Test
    void deleteCustomerById() throws Exception {
//    	List<Order> orders=orderRepository.findListOrderByIdCustomer("KH101");
//    	assertEquals(1,orders.size());
    }
}