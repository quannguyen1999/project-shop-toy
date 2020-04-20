package com.springframework.projectshoptoy.service;

import com.springframework.projectshoptoy.domain.Customer;
import com.springframework.projectshoptoy.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerServiceImplTest {
    CustomerServiceImpl customerServiceImpl;

    @Mock
    CustomerRepository customerRepository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        customerServiceImpl=new CustomerServiceImpl(customerRepository);
    }

    @Test
    void getCustomers() {
        Set<Customer> customerSet=new HashSet<>();
        Customer customer=new Customer();
//        customer.setAccount(account);
        customer.setAddress("33/16");
        customer.setCity("usa");
        customer.setEmail("employee@gmail.com");
        customer.setFirstName("kim");
        customer.setLastName("quyen");

        customerSet.add(customer);

        when(customerRepository.findAll()).thenReturn(customerSet);

        Set<Customer> resultListCustomer=customerServiceImpl.getCustomers();

        assertEquals(1,resultListCustomer.size());
        verify(customerRepository,times(1)).findAll();
    }
}