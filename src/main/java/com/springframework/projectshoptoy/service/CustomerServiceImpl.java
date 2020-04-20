package com.springframework.projectshoptoy.service;

import com.springframework.projectshoptoy.domain.Customer;
import com.springframework.projectshoptoy.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class CustomerServiceImpl implements  CustomerService{
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Set<Customer> getCustomers() {
        Set<Customer> customerSet=new HashSet<>();
        customerRepository.findAll().iterator().forEachRemaining(customerSet::add);
        return customerSet;
    }
}
