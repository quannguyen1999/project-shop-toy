package com.springframework.projectshoptoy.controller;

import com.springframework.projectshoptoy.domain.Account;
import com.springframework.projectshoptoy.domain.Customer;
import com.springframework.projectshoptoy.domain.ErrorException;
import com.springframework.projectshoptoy.service.AccountService;
import com.springframework.projectshoptoy.service.CustomerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;
@Api(description = "khách hàng")
@Slf4j
@RequiredArgsConstructor
@RequestMapping(CustomerController.BASE_URL)
@RestController
public class CustomerController {
    public final static String BASE_URL="/api/customers";
    private final CustomerService customerService;

    //lấy danh sách custmers
    @ApiOperation(value = "lấy danh sách custmers")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Set<Customer> getListCustomers(){
        return customerService.getCustomers();
    }

    //tìm khách hàng bằng id
    @ApiOperation(value = "tìm khách hàng bằng id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Customer findAccountById( @PathVariable String id){
        return customerService.findCustomerById(id);
    }

    //thêm mới customer
    @ApiOperation(value = "thêm mới customer")
    @PostMapping("/{userName}")
    @ResponseStatus(HttpStatus.CREATED)
    public Customer createNewCustomer(@PathVariable String userName,@Valid @RequestBody Customer customer){
        return customerService.createNewCustomer(userName,customer);
    }

    //cập nhập khách hàng
    @ApiOperation(value = "cập nhập khách hàng")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Customer updateAccount(@PathVariable String id,@RequestBody Customer customer){
        return customerService.updateCustomer(id,customer);
    }

    //xóa khách hàng bằng id
    @ApiOperation(value = "xóa khách hàng bằng id")
    @DeleteMapping("/{id}")
    public ResponseEntity<ErrorException> deleteCustomer(@PathVariable String id){
       log.debug("deleting id:"+id);
       customerService.deleteCustomerById(id);
       ErrorException errorException=new ErrorException();
       errorException.setStatus(HttpStatus.OK.toString());
       errorException.setError("delete success customer");
       return new ResponseEntity<ErrorException>(errorException,HttpStatus.OK);
    }

    //tìm kiếm customer bằng userName
    @ApiOperation(value = "tìm kiếm customer bằng userName")
    @GetMapping("/userName/{id}")
    public ResponseEntity<Customer> findCustomerByUserName(@PathVariable String id) throws Exception {
        return new ResponseEntity<Customer>(customerService.findCustomerByUserName(id),HttpStatus.OK);
    }
}
