package com.springframework.projectshoptoy.service;

import com.springframework.projectshoptoy.api.commandObject.OrderCommand;
import com.springframework.projectshoptoy.api.domain.Account;
import com.springframework.projectshoptoy.api.domain.Order;
import com.springframework.projectshoptoy.api.domain.OrderDetails;

import java.util.Set;

public interface OrderService {
    //lấy danh sách Order
    Set<OrderCommand> getListOrder();
    
    //lấy danh sách order by customer id
    Set<OrderCommand> getListOrderByCustomerID(String id);

    //lấy danh sách orderDetails
    Set<OrderDetails> getListOrderDetails(String orderID);

    //xóa Order
    boolean deleteOrder(String idOrder);

    //xóa orderDetails
    boolean deleteOrderDetails(String orderID,String idProduct);

    //tìm kiếm Order bằng id
    OrderCommand findOrderById(String id);

    //tạo Order
    OrderCommand createNewOrder(Order order);

    //tạo order detail
    OrderDetails createNewOrderDetail(String orderID,OrderDetails orderDetails);

    //cập nhập Order
    OrderCommand updateOrder(String id, Order order);

    //cập nhập orderDetail
    OrderDetails updateOrderDetails(String id,OrderDetails orderDetails);
}
