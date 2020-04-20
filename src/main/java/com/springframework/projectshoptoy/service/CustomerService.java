package com.springframework.projectshoptoy.service;

import com.springframework.projectshoptoy.domain.Customer;

import java.util.Set;

public interface CustomerService {
    //lấy danh sách khách hàng
    Set<Customer> getCustomers();

    //xóa khách hàng bằng id
    boolean deleteCustomerById(String id);

    //tìm khách hàng bằng id
    Customer findCustomerById(String id);

    //thêm khách hàng mới
    Customer createNewCustomer(String userName,Customer customer);

    //cập nhập khách hàng
    Customer updateCustomer(String id,Customer customer);

    //tìm customer bằng userName
    Customer findCustomerByUserName(String userName) throws  Exception;


}
