package com.springframework.projectshoptoy.controller;

import com.springframework.projectshoptoy.api.domain.Account;
import com.springframework.projectshoptoy.api.domain.Customer;
import com.springframework.projectshoptoy.api.domain.ErrorException;
import com.springframework.projectshoptoy.exception.ConflixIdException;
import com.springframework.projectshoptoy.service.AccountService;
import com.springframework.projectshoptoy.service.CustomerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ServletResponseMethodArgumentResolver;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;
//@Api:Mô tả api trong swagger-ui
//@Slf4j để dùng logger.info,...
//@RequiredArgsConstructor:để tạo constructor có tham số
//@RequestMapping:tên trang để truy cập
//@RestController :đăng ký Bean controler
@Api(description = "tài khoản")
@Slf4j
@RequiredArgsConstructor
@RequestMapping(AccountController.BASE_URL)
@RestController
public class AccountController {
    public final static String BASE_URL="/api/accounts";
    private final AccountService accountService;

    //@ApiOperaion:đổi tên trong swagger-ui
    //@GetMapping:Type:Get
    //@Responstatus trả về kiểu status
    //lấy danh sách account
    @ApiOperation(value = "lấy danh sách tài khoản")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Set<Account> accounts(){
        return accountService.getListAccount();
    }

    //ResponseEntity để trả về kiểu Json
    //xóa account bằng id
    @ApiOperation(value = "xóa tài khoản bằng id")
    @DeleteMapping("/{id}")
    public ResponseEntity<ErrorException> deleteAccount(@PathVariable String id){
        log.debug("deleting id:"+id);
        accountService.deleteAccount(id);
        ErrorException errorException=new ErrorException();
        errorException.setStatus(HttpStatus.OK.toString());
        errorException.setError("delete success");
        return new ResponseEntity<ErrorException>(errorException,HttpStatus.OK);
    }

    //Tìm kiếm account bằng id
    @ApiOperation(value = "tìm kiếm tài khoản bằng id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account findAccountById( @PathVariable String id){
        return accountService.findAccountByUserName(id);
    }

    //thêm account
    @ApiOperation(value = "thêm tài khoản")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Account createNewAccount(@Valid @RequestBody Account account){
        return accountService.createNewAccount(account);
    }

    //update account
    @ApiOperation(value = "cập nhập tài khoản")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account updateAccount(@PathVariable String id,@RequestBody Account account){
        return accountService.updateAccount(id,account);
    }
}
