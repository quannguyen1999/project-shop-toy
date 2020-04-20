package com.springframework.projectshoptoy.repositories;

import com.springframework.projectshoptoy.domain.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer,Long> {
}
