package com.springframework.projectshoptoy.controller;

import com.springframework.projectshoptoy.api.apiError.CustomRespone;
import com.springframework.projectshoptoy.api.domain.Account;
import com.springframework.projectshoptoy.exception.CustomValidateAndStatus;
import com.springframework.projectshoptoy.service.AccountService;
import com.springframework.projectshoptoy.validator.AccountValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
//@Api:Mô tả api trong swagger-ui
//@Slf4j để dùng logger.info,...
//@RequiredArgsConstructor:để tạo constructor có tham số
//@RequestMapping:tên trang để truy cập
//@RestController :đăng ký Bean controler
@Api(description = "tài khoản")
@RequiredArgsConstructor
@RequestMapping(AccountController.BASE_URL)
@RestController
public class AccountController {
    public final static String BASE_URL="/api/accounts";
    private final AccountService accountService;
    
    @Autowired
    private CustomValidateAndStatus customValidateAndStatus;
    
    @Autowired
    private AccountValidator accountValidator;

    //@ApiOperaion:đổi tên trong swagger-ui
    //@GetMapping:Type:Get
    //@Responstatus trả về kiểu status
    //lấy danh sách account
    @ApiOperation(value = "lấy danh sách tài khoản")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CustomRespone> accounts(){
        return customValidateAndStatus.checkCustomRespon(accountService.getListAccount());
    }

    //ResponseEntity để trả về kiểu Json
    //xóa account bằng id
    @ApiOperation(value = "xóa tài khoản bằng id")
    @DeleteMapping("/id")
    public ResponseEntity<CustomRespone> deleteAccount(@RequestBody Account account,BindingResult result){
    	accountValidator.validateDeleteAccount(account, result);
    	CustomRespone customReponse=customValidateAndStatus.getListApiBindingResult(result);
		return customReponse.getCode()==12 ? customValidateAndStatus.checkCustomRespon(customReponse) :  customValidateAndStatus.checkCustomRespon(accountService.deleteAccount(account.getUserName()));
    }

    //Tìm kiếm account bằng id
    @ApiOperation(value = "tìm kiếm tài khoản bằng id")
    @GetMapping("/id")
    public ResponseEntity<CustomRespone> findAccountById(@RequestBody Account account,BindingResult result){
    	accountValidator.validateFindAccountByUserName(account, result);
    	CustomRespone customReponse=customValidateAndStatus.getListApiBindingResult(result);
		return customReponse.getCode()==12 ? customValidateAndStatus.checkCustomRespon(customReponse) :  customValidateAndStatus.checkCustomRespon(accountService.findAccountByUserName(account.getUserName()));
    }

    //thêm account
    @ApiOperation(value = "thêm tài khoản")
    @PostMapping
    public ResponseEntity<CustomRespone> createNewAccount(@RequestBody @Valid Account account,Errors errors,BindingResult result){
    	accountValidator.validateCreateNewAccount(account, result);
    	CustomRespone customReponse=customValidateAndStatus.getListApiErrorAndBindingResult(errors,result);
		return customReponse.getCode()==12 ? customValidateAndStatus.checkCustomRespon(customReponse) : customValidateAndStatus.checkCustomRespon(accountService.createNewAccount(account));
    }

    //update account
    @ApiOperation(value = "cập nhập tài khoản")
    @PutMapping
    public ResponseEntity<CustomRespone> updateAccount(@Valid @RequestBody Account account,Errors errors,BindingResult result){
    	accountValidator.validateUpdateAccount(account, result);
    	CustomRespone customReponse=customValidateAndStatus.getListApiError(errors);
		return customReponse.getCode()==12 ? customValidateAndStatus.checkCustomRespon(customReponse) :  customValidateAndStatus.checkCustomRespon(accountService.updateAccount(account));
    }
}
