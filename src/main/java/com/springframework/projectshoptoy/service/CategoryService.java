package com.springframework.projectshoptoy.service;

import java.util.Set;

import com.springframework.projectshoptoy.api.apiError.CustomRespone;
import com.springframework.projectshoptoy.api.commandObject.CategoryCommand;
import com.springframework.projectshoptoy.api.domain.Category;
//Đảm bảo tính trừu tượng,kế thừa,bao đóng,Hợp thành
public interface CategoryService {
    //lấy danh sách account
    CustomRespone getListCategory();
    //xóa account
    CustomRespone deleteCategory(String id);
    //tìm kiếm category bằng id
    CustomRespone findCategoryByID(String id);
    //tạo category
    CustomRespone createNewCategory(Category category);
    //cập nhập category
    CustomRespone updateCategory(Category category);
}
