package com.springframework.projectshoptoy.service;

import com.springframework.projectshoptoy.dao.MyEntityManager;
import com.springframework.projectshoptoy.domain.Account;
import com.springframework.projectshoptoy.domain.Customer;
import com.springframework.projectshoptoy.domain.Order;
import com.springframework.projectshoptoy.exception.ConflixIdException;
import com.springframework.projectshoptoy.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
//	private final PasswordEncoder en;
//	
	@Autowired
	private MyEntityManager myEntityManager;
	
	@Autowired
	private PasswordEncoder en;

	@Override
	public Set<Account> getListAccount() {
		log.debug("get list accounts");
		Set<Account> accountSet=new HashSet<>();
		myEntityManager.getAllData(new Account()).forEach(accountSet::add);;
		return accountSet;
	}

	@Override
	public boolean deleteAccount(String userName) {
		Account account=findAccountByUserName(userName);
		List<Customer> listCustomer= myEntityManager.query("db.customers.find({'username':'"+userName+"'})",new Customer());
		if(listCustomer.size()>=1) {
			myEntityManager.deleteT(listCustomer.get(0),listCustomer.get(0).getCustomerID());
		}
		return	myEntityManager.deleteT(account,account.getUserName());
	}

	@Override
	public Account findAccountByUserName(String userName) {
		return (Account) myEntityManager.findById(new Account(), userName).orElseThrow(()->new NotFoundException("can find id "+userName));
	}

	@Override
	public Account createNewAccount(Account account) {
		myEntityManager=new MyEntityManager();
		if(myEntityManager.findById(new Account(),account.getUserName()).isPresent()==true){
			log.error("conflix username");
			throw new ConflixIdException("conflix username");
		}
		account.setPassword(en.encode(account.getPassword()));
		if(myEntityManager.addT(account,account.getUserName())==true) {
			return account;
		};
		return null;
	}

	@Override
	public Account updateAccount(String id, Account account) {
		Account accountFind=(Account) myEntityManager.findById(new Account(),id).orElseThrow(()->new NotFoundException("Not found id "+id));
		if(account.getPassword()!=null){
			accountFind.setPassword(en.encode(account.getPassword()));
		}
		accountFind.setAccType(account.isAccType());
		return myEntityManager.updateT(accountFind,accountFind.getUserName()).get();
	}

}
