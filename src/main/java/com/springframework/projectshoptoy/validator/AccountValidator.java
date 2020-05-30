package com.springframework.projectshoptoy.validator;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.springframework.projectshoptoy.api.apiError.CustomRespone;
import com.springframework.projectshoptoy.api.domain.Account;
import com.springframework.projectshoptoy.dao.MyEntityManager;

@Component
public class AccountValidator implements Validator{

	@Autowired
	private MyEntityManager myEntityManager;
	
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Account.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
	}
	
	public void validateCreateNewAccount(Object target,Errors errors) {
		Account account=(Account)target;
		if(myEntityManager.findById(new Account(),account.getUserName()).isPresent()==true){
			errors.rejectValue("userName","5","trùng userName");
		}
	}
	
	public void validateDeleteAccount(Object target,Errors errors) {
		Account account=(Account) target;
		if(account.getUserName()==null) {
			errors.rejectValue("userName","1","userName không được rỗng");
		}
		Optional<Object> accounFind=myEntityManager.findById(new Account(), account.getUserName());
		if(accounFind.isEmpty()) {
			errors.rejectValue("userName","3","username không tồn tại");
		}
	}
	
	public void validateFindAccountByUserName(Object target,Errors errors) {
		Account account=(Account)target;
		if(account.getUserName()==null) {
			errors.rejectValue("userName", "1","userName không được rỗng");
		}
		Optional<Object> accounFind=myEntityManager.findById(new Account(), account.getUserName());
		if(accounFind.isEmpty()) {
			errors.rejectValue("userName","3","username không tồn tại");
		}
	}
	
	public void validateUpdateAccount(Object target,Errors errors) {
		Account account=(Account)target;
		if(account.getUserName()==null) {
			errors.rejectValue("userName", "1","userName không được rỗng");
		}
		Optional<Object> findAccountOptinal= myEntityManager.findById(new Account(),account.getUserName());
		if(findAccountOptinal.isPresent()==false) {
			errors.rejectValue("userName","3","Không tìm thấy userName này");
		}
	}
	
	

}
