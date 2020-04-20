package com.springframework.projectshoptoy.service;

import com.springframework.projectshoptoy.domain.Account;
import com.springframework.projectshoptoy.domain.Category;

import java.util.Set;

public interface CategoryService {
    //lấy danh sách account
    Set<Category> getListCategory();

    //xóa account
    boolean deleteCategory(String id);

    //tìm kiếm category bằng id
    Category findCategoryByID(String id);

    //tạo category
    Category createNewCategory(Category category);

    //cập nhập category
    Category updateCategory(String id, Category category);
}
