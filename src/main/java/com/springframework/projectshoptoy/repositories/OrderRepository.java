package com.springframework.projectshoptoy.repositories;

import com.springframework.projectshoptoy.domain.Account;
import com.springframework.projectshoptoy.domain.Customer;
import com.springframework.projectshoptoy.domain.Order;

import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order,String> {
	@Query(value = "{ 'customer.$id' : ?0 }")
	List<Order> findListOrderByIdCustomer(String idCustomer);
}

