package com.springframework.projectshoptoy.service;

import com.springframework.projectshoptoy.domain.Account;
import com.springframework.projectshoptoy.domain.Product;

import java.util.Set;

public interface ProductService {
    //lấy danh sách Product
    Set<Product> getListProduct();

    //xóa Product
    boolean deleteProduct(String userName);

    //tìm kiếm Product bằng userName
    Product findProductByID(String id);

    //tạo Product
    Product createNewProduct(Product product);

    //cập nhập Product
    Product updateProduct(String id, Product product);

}
