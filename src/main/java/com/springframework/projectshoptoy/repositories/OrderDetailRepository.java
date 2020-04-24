package com.springframework.projectshoptoy.repositories;

import com.springframework.projectshoptoy.domain.Account;
import com.springframework.projectshoptoy.domain.Customer;
import com.springframework.projectshoptoy.domain.OrderDetails;
import com.springframework.projectshoptoy.domain.Product;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderDetailRepository extends CrudRepository<OrderDetails,String> {
    @Query(value = "{ 'order.$id' : ?0 }")
    List<OrderDetails> listAllOrderDetailsByIdOrder(String idOrder);
    
    @Query(value = "{ 'product.$id' : ?0 }")
    List<OrderDetails> listAllOrderDetailsByProductId(String productID);
}
