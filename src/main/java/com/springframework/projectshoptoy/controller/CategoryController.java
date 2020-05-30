package com.springframework.projectshoptoy.controller;

import com.springframework.projectshoptoy.api.apiError.CustomRespone;
import com.springframework.projectshoptoy.api.commandObject.CategoryCommand;
import com.springframework.projectshoptoy.api.domain.Category;
import com.springframework.projectshoptoy.api.domain.ErrorException;
import com.springframework.projectshoptoy.exception.CustomValidateAndStatus;
import com.springframework.projectshoptoy.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Set;
//@Api:Mô tả api trong swagger-ui
//@Slf4j để dùng logger.info,...
//@RequiredArgsConstructor:để tạo constructor có tham số
//@RequestMapping:tên trang để truy cập
//@RestController :đăng ký Bean controler
@Api(description = "mặt hàng")
@Slf4j
@RequiredArgsConstructor
@RequestMapping(CategoryController.BASE_URL)
@RestController
public class CategoryController {
    public final static String  BASE_URL="/api/categories";
    private final CategoryService categoryService;
    @Autowired
    private CustomValidateAndStatus customValidateAndStatus;
    //@ApiOperaion:đổi tên trong swagger-ui
    //@GetMapping:Type:Get
    //@Responstatus trả về kiểu status
    //lấy danh sách category
    @ApiOperation(value = "lấy danh sách mặt hàng")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CustomRespone categories(){
        return categoryService.getListCategory();
    }

    //thêm mới category
    @ApiOperation(value = "thêm mới mặt hàng")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CustomRespone> createNewCategory(@Valid @RequestBody Category category,Errors errors){
    	CustomRespone customReponse=customValidateAndStatus.getListApiError(errors);
		return customReponse.getCode()==12 ? customValidateAndStatus.checkCustomRespon(customReponse) : customValidateAndStatus.checkCustomRespon(categoryService.createNewCategory(category));
    }

    //tìm kiếm category bằng id
    @ApiOperation(value = "tìm kiếm category bằng id")
    @GetMapping("/id")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CustomRespone> findCategoryByID(@RequestBody Category category){
    	return customValidateAndStatus.checkCustomRespon(categoryService.findCategoryByID(category.getCategoryID()));
    }

    //xóa category bằng id
    @ApiOperation(value = "xóa category bằng id")
    @DeleteMapping("/id")
    public ResponseEntity<CustomRespone> deleteCategory(@RequestBody Category category){
        categoryService.deleteCategory(category.getCategoryID());
        return customValidateAndStatus.checkCustomRespon(categoryService.deleteCategory(category.getCategoryID()));
    }

    //cập nhập category
    @ApiOperation(value = "cập nhập category")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CustomRespone> updateCategory(@Valid @RequestBody Category category,Errors errors){
    	CustomRespone customReponse=customValidateAndStatus.getListApiError(errors);
		return customReponse.getCode()==12 ? customValidateAndStatus.checkCustomRespon(customReponse) : customValidateAndStatus.checkCustomRespon(categoryService.updateCategory(category));
    }



}
