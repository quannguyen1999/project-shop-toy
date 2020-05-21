package com.springframework.projectshoptoy.controller;

import com.springframework.projectshoptoy.api.commandObject.SupplierCommand;
import com.springframework.projectshoptoy.api.domain.Category;
import com.springframework.projectshoptoy.api.domain.ErrorException;
import com.springframework.projectshoptoy.api.domain.Supplier;
import com.springframework.projectshoptoy.service.CategoryService;
import com.springframework.projectshoptoy.service.SupplierService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Api(description = "nhà cung cấp")
@Slf4j
@RequiredArgsConstructor
@RequestMapping(SupplierController.BASE_URL)
@RestController
public class SupplierController {
    public final static String  BASE_URL="/api/suppliers";
    private final SupplierService supplierService;

    //@ApiOperaion:đổi tên trong swagger-ui
    //@GetMapping:Type:Get
    //@Responstatus trả về kiểu status
    //lấy danh sách Supplier
    @ApiOperation(value = "lấy danh sách Supplier")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Set<SupplierCommand> suppliers(){
        return supplierService.getListSupplier();
    }

    //thêm mới Supplier
    @ApiOperation(value = "thêm mới Supplier")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SupplierCommand createNewSupplier(@Valid @RequestBody Supplier supplier){
        return supplierService.createNewSupplier(supplier);
    }

    //tìm kiếm Supplier bằng id
    @ApiOperation(value = "tìm kiếm Supplier bằng id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SupplierCommand findSupplierByID(@PathVariable String id){
        return supplierService.findSupplierById(id);
    }

    //xóa Supplier bằng id
    @ApiOperation(value = "xóa Supplier bằng id")
    @DeleteMapping("/{id}")
    public ResponseEntity<ErrorException> deleteSupplier(@PathVariable String id){
        log.debug("deleting id:"+id);
        supplierService.deleteSupplier(id);
        ErrorException errorException=new ErrorException();
        errorException.setStatus(HttpStatus.OK.toString());
        errorException.setError("delete success");
        return new ResponseEntity<ErrorException>(errorException,HttpStatus.OK);
    }

    //cập nhập Supplier
    @ApiOperation(value = "cập nhập Supplier")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SupplierCommand updateSupplier(@PathVariable String id,@RequestBody Supplier supplier){
        return supplierService.updateSupplier(id,supplier);
    }


}
