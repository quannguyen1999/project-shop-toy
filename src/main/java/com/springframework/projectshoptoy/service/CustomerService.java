package com.springframework.projectshoptoy.service;

import com.springframework.projectshoptoy.domain.Customer;

import java.util.Set;

public interface CustomerService {
    Set<Customer> getCustomers();

    boolean deleteCustomerById(String id);

    Customer findCustomerByUserName(String userName) throws  Exception;
}
