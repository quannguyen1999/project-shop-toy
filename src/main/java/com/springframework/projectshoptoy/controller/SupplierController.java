package com.springframework.projectshoptoy.controller;

import com.springframework.projectshoptoy.domain.Category;
import com.springframework.projectshoptoy.domain.ErrorException;
import com.springframework.projectshoptoy.domain.Supplier;
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
@Api(description = "nhà cung cấp")
@Slf4j
@RequiredArgsConstructor
@RequestMapping(SupplierController.BASE_URL)
@RestController
public class SupplierController {
    public final static String  BASE_URL="/api/suppliers";
    private final SupplierService supplierService;

    //lấy danh sách Supplier
    @ApiOperation(value = "lấy danh sách Supplier")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Set<Supplier> suppliers(){
        return supplierService.getListSupplier();
    }

    //thêm mới Supplier
    @ApiOperation(value = "thêm mới Supplier")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Supplier createNewSupplier(@Valid @RequestBody Supplier supplier){
        return supplierService.createNewSupplier(supplier);
    }

    //tìm kiếm Supplier bằng id
    @ApiOperation(value = "tìm kiếm Supplier bằng id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Supplier findSupplierByID(@PathVariable String id){
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
    public Supplier updateSupplier(@PathVariable String id,@RequestBody Supplier supplier){
        return supplierService.updateSupplier(id,supplier);
    }


}
