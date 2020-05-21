package com.springframework.projectshoptoy.service;

import com.springframework.projectshoptoy.api.domain.Account;
import com.springframework.projectshoptoy.exception.ConflixIdException;

import java.util.Set;

//Đảm bảo tính trừu tượng,kế thừa,bao đóng,Hợp thành
public interface AccountService {
    //lấy danh sách account
    Set<Account> getListAccount();

    //xóa account
    boolean deleteAccount(String userName);

    //tìm kiếm account bằng userName
    Account findAccountByUserName(String userName);

    //tạo account
    Account createNewAccount(Account account);

    //cập nhập account
    Account updateAccount(String id,Account account);

}
