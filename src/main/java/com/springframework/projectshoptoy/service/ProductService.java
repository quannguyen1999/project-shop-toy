package com.springframework.projectshoptoy.service;

import java.util.Set;

import com.springframework.projectshoptoy.api.commandObject.ProductCommand;
import com.springframework.projectshoptoy.api.domain.Account;
import com.springframework.projectshoptoy.api.domain.Product;

public interface ProductService {
    //lấy danh sách Product
    Set<ProductCommand> getListProduct();

    //xóa Product
    boolean deleteProduct(String idProduct);

    //tìm kiếm Product bằng userName
    ProductCommand findProductByID(String id);

    //tạo Product
    ProductCommand createNewProduct(Product product);

    //cập nhập Product
    ProductCommand updateProduct(String id, Product product);

}
