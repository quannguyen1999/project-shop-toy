package com.springframework.projectshoptoy.service;

import java.util.Set;

import com.springframework.projectshoptoy.api.commandObject.CustomerCommand;
import com.springframework.projectshoptoy.api.domain.Customer;

public interface CustomerService {
    //lấy danh sách khách hàng
    Set<CustomerCommand> getCustomers();

    //xóa khách hàng bằng id
    boolean deleteCustomerById(String id);

    //tìm khách hàng bằng id
    CustomerCommand findCustomerById(String id);

    //thêm khách hàng mới
    CustomerCommand createNewCustomer(String userName,Customer customer);

    //cập nhập khách hàng
    CustomerCommand updateCustomer(String username,Customer customer);

    //tìm customer bằng userName
    CustomerCommand findCustomerByUserName(String userName);


}
