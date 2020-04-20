package com.springframework.projectshoptoy.repositories;

import com.springframework.projectshoptoy.domain.Account;
import com.springframework.projectshoptoy.domain.OrderDetails;
import org.springframework.data.repository.CrudRepository;

public interface OrderDetailRepository extends CrudRepository<OrderDetails,String> {
}
