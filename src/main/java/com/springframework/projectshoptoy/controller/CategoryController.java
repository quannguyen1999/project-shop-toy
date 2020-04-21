package com.springframework.projectshoptoy.controller;

import com.springframework.projectshoptoy.domain.Category;
import com.springframework.projectshoptoy.domain.ErrorException;
import com.springframework.projectshoptoy.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(CategoryController.BASE_URL)
@RestController
public class CategoryController {
    public final static String  BASE_URL="/api/categories";
    private final CategoryService categoryService;

    //lấy danh sách category
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Set<Category> categories(){
        return categoryService.getListCategory();
    }

    //thêm mới category
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Category createNewCategory(@Valid @RequestBody Category category){
        return categoryService.createNewCategory(category);
    }

    //tìm kiếm category bằng id
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Category findCategoryByID(@PathVariable String id){
        return categoryService.findCategoryByID(id);
    }

    //xóa category bằng id
    @DeleteMapping("/{id}")
    public ResponseEntity<ErrorException> deleteCategory(@PathVariable String id){
        log.debug("deleting id:"+id);
//        categoryService.deleteCategory(id);
        ErrorException errorException=new ErrorException();
        errorException.setStatus(HttpStatus.OK.toString());
        errorException.setError("delete success");
        return new ResponseEntity<ErrorException>(errorException,HttpStatus.OK);
    }

    //cập nhập category
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Category updateCategory(@PathVariable String id,@RequestBody Category category){
        return categoryService.updateCategory(id,category);
    }



}
