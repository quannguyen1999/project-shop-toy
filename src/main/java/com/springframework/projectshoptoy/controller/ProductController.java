package com.springframework.projectshoptoy.controller;

import com.springframework.projectshoptoy.api.commandObject.ProductCommand;
import com.springframework.projectshoptoy.api.domain.ErrorException;
import com.springframework.projectshoptoy.api.domain.Product;
import com.springframework.projectshoptoy.api.domain.Supplier;
import com.springframework.projectshoptoy.api.mapper.ProductMapper;
import com.springframework.projectshoptoy.service.ProductService;
import com.springframework.projectshoptoy.service.SupplierService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;
//@Api:Mô tả api trong swagger-ui
//@Slf4j để dùng logger.info,...
//@RequiredArgsConstructor:để tạo constructor có tham số
//@RequestMapping:tên trang để truy cập
//@RestController :đăng ký Bean controler
@Api(description = "sản phẩm")
@Slf4j
@RequiredArgsConstructor
@RequestMapping(ProductController.BASE_URL)
@RestController
public class ProductController {
	
	@Autowired
	private ProductMapper productMapper;
	
    public final static String  BASE_URL="/api/products";
    private final ProductService productService;

    //@ApiOperaion:đổi tên trong swagger-ui
    //@GetMapping:Type:Get
    //@Responstatus trả về kiểu status
    //lấy danh sách Product
    @ApiOperation(value = "lấy danh sách Product")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Set<ProductCommand> products(){
        return productService.getListProduct();
    }

    //thêm mới Product
    @ApiOperation(value = "thêm mới Product")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductCommand createNewProduct(@Valid @RequestBody ProductCommand productCommand){
    	System.out.println(productCommand);
        return productService.createNewProduct(productMapper.productCommandToProduct(productCommand));
    }

    //tìm kiếm Product bằng id
    @ApiOperation(value = "tìm kiếm Product bằng id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductCommand findSupplierByID(@PathVariable String id){
        return productService.findProductByID(id);
    }

    //xóa Product bằng id
    @ApiOperation(value = "xóa Product bằng id")
    @DeleteMapping("/{id}")
    public ResponseEntity<ErrorException> deleteProduct(@PathVariable String id){
        log.debug("deleting id:"+id);
        ErrorException errorException=new ErrorException();
        if(productService.deleteProduct(id)==true){
        	 errorException.setError("delete success");
        	  return new ResponseEntity<ErrorException>(errorException,HttpStatus.OK);
        };
        errorException.setError("can't delete");
        return new ResponseEntity<ErrorException>(errorException,HttpStatus.CONFLICT);
    }

    //cập nhập Product
    @ApiOperation(value = "cập nhập Product")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductCommand updateProduct(@PathVariable String id,@RequestBody Product product){
        return productService.updateProduct(id,product);
    }

}
