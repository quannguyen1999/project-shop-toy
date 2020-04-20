package com.springframework.projectshoptoy.repositories;

import com.springframework.projectshoptoy.domain.Account;
import com.springframework.projectshoptoy.domain.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order,String> {

}
