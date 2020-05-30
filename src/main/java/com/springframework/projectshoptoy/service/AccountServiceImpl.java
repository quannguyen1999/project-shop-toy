package com.springframework.projectshoptoy.service;

import com.springframework.projectshoptoy.api.apiError.CustomRespone;
import com.springframework.projectshoptoy.api.domain.Account;
import com.springframework.projectshoptoy.api.domain.Customer;
import com.springframework.projectshoptoy.api.domain.Order;
import com.springframework.projectshoptoy.dao.MyEntityManager;
import com.springframework.projectshoptoy.exception.ConflixIdException;
import com.springframework.projectshoptoy.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityTransaction;
@RequiredArgsConstructor
@Slf4j
@Service
public class AccountServiceImpl implements  AccountService{
	@Autowired
	private MyEntityManager myEntityManager;
	
	@Autowired
	private PasswordEncoder en;

	@Override
	public CustomRespone getListAccount() {
		log.debug("get list accounts");
		List<Account> listAccount=new ArrayList<Account>();
		myEntityManager.getAllData(new Account()).forEach(t->{
			listAccount.add(t);
		});
		return new CustomRespone(10,"danh sách account",listAccount);
	}

	@Override
	public CustomRespone deleteAccount(String userName) {
		CustomRespone accountCustom=findAccountByUserName(userName);
		if(accountCustom.getCode()!=9) {
			return accountCustom;
		}
		List<Customer> listCustomer= myEntityManager.query("db.customers.find({'username':'"+userName+"'})",new Customer());
		if(listCustomer.size()>=1) {
			myEntityManager.deleteT(listCustomer.get(0),listCustomer.get(0).getCustomerID());
		}
		Account account=(Account)accountCustom.getObject();
		myEntityManager.deleteT(account,account.getUserName());
		return	new CustomRespone(7, "Xóa thành công",null);
	}

	@Override
	public CustomRespone findAccountByUserName(String userName) {
		CustomRespone customRespone=null;
		if(userName==null) {
			return new CustomRespone(1, "userName không được rỗng", null);
		}
		Optional<Object> accounFind=myEntityManager.findById(new Account(), userName);
		if(accounFind.isEmpty()) {
			customRespone=new CustomRespone(3,"username không tồn tại",null);
		}else {
			customRespone=new CustomRespone(9,"tìm thấy", (Account)accounFind.get());
		}
		return customRespone;
	}

	@Override
	public CustomRespone createNewAccount(Account account) {
		myEntityManager=new MyEntityManager();
		if(myEntityManager.findById(new Account(),account.getUserName()).isPresent()==true){
			return new CustomRespone(3, "trùng username", null);
		}
		account.setPassword(en.encode(account.getPassword()));
		if(myEntityManager.addT(account,account.getUserName())==true) {
			return new CustomRespone(6,"Thêm thành công", account);
		};
		return new CustomRespone(14	,"Thêm không thành công", null);
	}

	@Override
	public CustomRespone updateAccount(Account account) {
		if(account.getUserName()==null) {
			return new CustomRespone(1, "username không được rỗng", null);
		}
		Optional<Object> findAccountOptinal= myEntityManager.findById(new Account(),account.getUserName());
		if(findAccountOptinal.isPresent()==false) {
			return new CustomRespone(3, "Không tìm thấy", null);
		}
		Account accountFind=(Account)findAccountOptinal.get();
		if(account.getPassword()!=null){
			accountFind.setPassword(en.encode(account.getPassword()));
		}
		if(account.getEmail()!=null) {
			accountFind.setEmail(account.getPassword());
		}
		if(accountFind.isAccType()!=account.isAccType()) {
			accountFind.setAccType(account.isAccType());
		}
		accountFind.setAccType(account.isAccType());
		return new CustomRespone(8, "Cập nhập thành công", myEntityManager.updateT(accountFind,accountFind.getUserName()).get());
	}

}
