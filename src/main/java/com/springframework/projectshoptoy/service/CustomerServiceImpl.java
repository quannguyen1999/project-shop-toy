package com.springframework.projectshoptoy.service;

import com.springframework.projectshoptoy.domain.Customer;
import com.springframework.projectshoptoy.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class CustomerServiceImpl implements  CustomerService{
    MongoTemplate mongoTemplate;

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

    @Override
    public boolean deleteCustomerById(String id) {
        Optional<Customer> customer=customerRepository.findById(id);
        if(customer.get()!=null){
            customerRepository.delete(customer.get());
            return true;
        }
        return false;
    }

    @Override
    public Customer findCustomerByUserName(String userName) throws  Exception{
        Customer customer= customerRepository.findCustomerByUserName(userName);
        if(customer!=null){
            return customer;
        }
        return null;
    }
}
