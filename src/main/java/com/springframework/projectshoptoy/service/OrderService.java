package com.springframework.projectshoptoy.service;

import com.springframework.projectshoptoy.domain.Account;
import com.springframework.projectshoptoy.domain.Order;
import com.springframework.projectshoptoy.domain.OrderDetails;

import java.util.Set;

public interface OrderService {
    //lấy danh sách Order
    Set<Order> getListOrder();

    //lấy danh sách orderDetails
    Set<OrderDetails> getListOrderDetails(String orderID);

    //xóa Order
    boolean deleteOrder(String idOrder);

    //xóa orderDetails
    boolean deleteOrderDetails(String orderID,String idOrderDetails);

    //tìm kiếm Order bằng id
    Order findOrderById(String id);

    //tìm kiếm OrderDetails bằng id
    OrderDetails findOrderDetailById(String orderID,String idOrderDetails);

    //tạo Order
    Order createNewOrder(Order order);

    //tạo order detail
    OrderDetails createNewOrderDetail(String orderID,OrderDetails orderDetails);

    //cập nhập Order
    Order updateOrder(String id, Order order);

    //cập nhập orderDetail
    OrderDetails updateOrderDetails(String id,String orderDetailID,OrderDetails orderDetails);
}
