package com.springframework.projectshoptoy.controller;

import com.springframework.projectshoptoy.domain.ErrorException;
import com.springframework.projectshoptoy.domain.Product;
import com.springframework.projectshoptoy.domain.Supplier;
import com.springframework.projectshoptoy.service.ProductService;
import com.springframework.projectshoptoy.service.SupplierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(ProductController.BASE_URL)
@RestController
public class ProductController {
    public final static String  BASE_URL="/api/products";
    private final ProductService productService;

    //lấy danh sách Product
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Set<Product> products(){
        return productService.getListProduct();
    }

    //thêm mới Product
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createNewProduct(@Valid @RequestBody Product product){
        return productService.createNewProduct(product);
    }

    //tìm kiếm Product bằng id
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product findSupplierByID(@PathVariable String id){
        return productService.findProductByID(id);
    }

    //xóa Product bằng id
    @DeleteMapping("/{id}")
    public ResponseEntity<ErrorException> deleteProduct(@PathVariable String id){
        log.debug("deleting id:"+id);
//        categoryService.deleteCategory(id);
        ErrorException errorException=new ErrorException();
        errorException.setStatus(HttpStatus.OK.toString());
        errorException.setError("delete success");
        return new ResponseEntity<ErrorException>(errorException,HttpStatus.OK);
    }

    //cập nhập Product
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product updateProduct(@PathVariable String id,@RequestBody Product product){
        return productService.updateProduct(id,product);
    }

}
