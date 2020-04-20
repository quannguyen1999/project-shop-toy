package com.springframework.projectshoptoy.service;

import com.springframework.projectshoptoy.domain.Account;
import com.springframework.projectshoptoy.domain.Supplier;

import java.util.Set;

public interface SupplierService {
    //lấy danh sách Supplier
    Set<Supplier> getListSupplier();

    //xóa Supplier
    boolean deleteSupplier(String userName);

    //tìm kiếm Supplier bằng userName
    Supplier findSupplierById(String Id);

    //tạo Supplier
    Supplier createNewSupplier(Supplier supplier);

    //cập nhập Supplier
    Supplier updateSupplier(String id, Supplier supplier);

}
