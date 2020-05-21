package com.springframework.projectshoptoy.service;

import java.util.Set;
import com.springframework.projectshoptoy.api.commandObject.CategoryCommand;
import com.springframework.projectshoptoy.api.domain.Category;
//Đảm bảo tính trừu tượng,kế thừa,bao đóng,Hợp thành
public interface CategoryService {
    //lấy danh sách account
    Set<CategoryCommand> getListCategory();

    //xóa account
    boolean deleteCategory(String id);

    //tìm kiếm category bằng id
    CategoryCommand findCategoryByID(String id);

    //tạo category
    CategoryCommand createNewCategory(Category category);

    //cập nhập category
    CategoryCommand updateCategory(String id, Category category);
}
