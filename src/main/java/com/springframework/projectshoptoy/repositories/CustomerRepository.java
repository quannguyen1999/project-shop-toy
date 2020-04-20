package com.springframework.projectshoptoy.repositories;

import com.springframework.projectshoptoy.domain.Customer;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer,String> {
    @Query(value = "{ 'account.$id' : ?0 }")
    Customer findCustomerByUserName(String userName);
}
