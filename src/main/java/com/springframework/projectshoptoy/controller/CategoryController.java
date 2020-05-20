package com.springframework.projectshoptoy.controller;

import com.springframework.projectshoptoy.api.commandObject.CategoryCommand;
import com.springframework.projectshoptoy.api.domain.Category;
import com.springframework.projectshoptoy.api.domain.ErrorException;
import com.springframework.projectshoptoy.service.CategoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;
@Api(description = "mặt hàng")
@Slf4j
@RequiredArgsConstructor
@RequestMapping(CategoryController.BASE_URL)
@RestController
public class CategoryController {
    public final static String  BASE_URL="/api/categories";
    private final CategoryService categoryService;

    //lấy danh sách category
    @ApiOperation(value = "lấy danh sách mặt hàng")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Set<CategoryCommand> categories(){
        return categoryService.getListCategory();
    }

    //thêm mới category
    @ApiOperation(value = "thêm mới mặt hàng")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryCommand createNewCategory(@Valid @RequestBody Category category){
        return categoryService.createNewCategory(category);
    }

    //tìm kiếm category bằng id
    @ApiOperation(value = "tìm kiếm category bằng id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryCommand findCategoryByID(@PathVariable String id){
        return categoryService.findCategoryByID(id);
    }

    //xóa category bằng id
    @ApiOperation(value = "xóa category bằng id")
    @DeleteMapping("/{id}")
    public ResponseEntity<ErrorException> deleteCategory(@PathVariable String id){
        log.debug("deleting id:"+id);
        categoryService.deleteCategory(id);
        ErrorException errorException=new ErrorException();
        errorException.setStatus(HttpStatus.OK.toString());
        errorException.setError("delete success");
        return new ResponseEntity<ErrorException>(errorException,HttpStatus.OK);
    }

    //cập nhập category
    @ApiOperation(value = "cập nhập category")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryCommand updateCategory(@PathVariable String id,@RequestBody Category category){
        return categoryService.updateCategory(id,category);
    }



}
