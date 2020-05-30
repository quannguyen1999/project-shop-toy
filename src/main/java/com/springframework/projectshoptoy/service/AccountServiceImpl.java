package com.springframework.projectshoptoy.service;

import com.springframework.projectshoptoy.api.apiError.CustomRespone;
import com.springframework.projectshoptoy.api.domain.Account;
import com.springframework.projectshoptoy.api.domain.Customer;
import com.springframework.projectshoptoy.dao.MyEntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements  AccountService{
	@Autowired
	private MyEntityManager myEntityManager;

	@Autowired
	private PasswordEncoder en;

	@Override
	public CustomRespone getListAccount() {
		List<Account> listAccount=new ArrayList<Account>();
		myEntityManager.getAllData(new Account()).forEach(t->{
			listAccount.add(t);
		});
		return new CustomRespone(10,"danh sách account",listAccount);
	}

	@Override
	public CustomRespone deleteAccount(String userName) {
		CustomRespone accountCustom=findAccountByUserName(userName);
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
		Optional<Object> accounFind=myEntityManager.findById(new Account(), userName);
		customRespone=new CustomRespone(9,"tìm thấy", (Account)accounFind.get());
		return customRespone;
	}

	@Override
	public CustomRespone createNewAccount(Account account) {
		account.setPassword(en.encode(account.getPassword()));
		if(myEntityManager.addT(account, account.getUserName())){
			return new CustomRespone(6, "thêm thành công", null);
		}else {
			return new CustomRespone(14, "thêm không thành công", null);
		}
	}

	@Override
	public CustomRespone updateAccount(Account account) {
		Optional<Object> findAccountOptinal= myEntityManager.findById(new Account(),account.getUserName());
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
