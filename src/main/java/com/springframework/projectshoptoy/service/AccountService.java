package com.springframework.projectshoptoy.service;

import com.springframework.projectshoptoy.api.apiError.CustomRespone;
import com.springframework.projectshoptoy.api.domain.Account;
import com.springframework.projectshoptoy.exception.ConflixIdException;

import java.util.Set;

//Đảm bảo tính trừu tượng,kế thừa,bao đóng,Hợp thành
public interface AccountService {
    //lấy danh sách account
    CustomRespone getListAccount();

    //xóa account
    CustomRespone deleteAccount(String userName);

    //tìm kiếm account bằng userName
    CustomRespone findAccountByUserName(String userName);

    //tạo account
    CustomRespone createNewAccount(Account account);

    //cập nhập asccount
    CustomRespone updateAccount(Account account);

}
