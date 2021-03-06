package com.springframework.projectshoptoy.service;

import java.util.Set;

import com.springframework.projectshoptoy.api.commandObject.SupplierCommand;
import com.springframework.projectshoptoy.api.domain.Supplier;
//Đảm bảo tính trừu tượng,kế thừa,bao đóng,Hợp thành
public interface SupplierService {
    //lấy danh sách Supplier
    Set<SupplierCommand> getListSupplier();

    //xóa Supplier
    boolean deleteSupplier(String idSupplier);

    //tìm kiếm Supplier bằng userName
    SupplierCommand findSupplierById(String Id);

    //tạo Supplier
    SupplierCommand createNewSupplier(Supplier supplier);

    //cập nhập Supplier
    SupplierCommand updateSupplier(String id, Supplier supplier);

}
