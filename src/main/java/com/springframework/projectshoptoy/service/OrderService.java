package com.springframework.projectshoptoy.service;

import com.springframework.projectshoptoy.domain.Account;
import com.springframework.projectshoptoy.domain.Order;

import java.util.Set;

public interface OrderService {
    //lấy danh sách Order
    Set<Order> getListOrder();

    //xóa Order
    boolean deleteOrder(String userName);

    //tìm kiếm Order bằng id
    Order findOrderById(String id);

    //tạo Order
    Order createNewOrder(Order order);

    //cập nhập Order
    Order updateOrder(String id, Order order);

}
