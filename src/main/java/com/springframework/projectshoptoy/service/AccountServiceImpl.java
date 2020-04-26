package com.springframework.projectshoptoy.service;

import com.springframework.projectshoptoy.domain.Account;
import com.springframework.projectshoptoy.domain.Customer;
import com.springframework.projectshoptoy.domain.Order;
import com.springframework.projectshoptoy.exception.ConflixIdException;
import com.springframework.projectshoptoy.exception.NotFoundException;
import com.springframework.projectshoptoy.repositories.AccountRepository;
import com.springframework.projectshoptoy.repositories.CustomerRepository;
import com.springframework.projectshoptoy.repositories.OrderDetailRepository;
import com.springframework.projectshoptoy.repositories.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@Slf4j
@Service
public class AccountServiceImpl implements  AccountService{
	private final AccountRepository accountRepository;
	private final CustomerRepository customerRepository;
	private final OrderRepository orderRepository;
	private final OrderDetailRepository orderDetailsRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public Set<Account> getListAccount() {
		log.debug("get list accounts");
		Set<Account> accountSet=new HashSet<>();
		accountRepository.findAll().iterator().forEachRemaining(accountSet::add);
		return accountSet;
	}

	@Override
	public boolean deleteAccount(String userName) {
		Account account=accountRepository.findById(userName).orElseThrow(()->new NotFoundException("can find id "+userName));
		Customer customer=customerRepository.findCustomerByUserName(userName);
		if(customer!=null){
			orderRepository.findListOrderByIdCustomer(customer.getCustomerID())
			.parallelStream()
			.forEach(
					order->{
						orderDetailsRepository.listAllOrderDetailsByIdOrder(order.getOrderID())
						.parallelStream()
						.forEach(orderDetailsRepository::delete);
						orderRepository.delete(order);
					}
					);
			customerRepository.delete(customer);
		}
		accountRepository.delete(account);
		return true;
	}

	@Override
	public Account findAccountByUserName(String userName) {
		return accountRepository.findById(userName).orElseThrow(()->new NotFoundException("can find id "+userName));
	}

	@Override
	public Account createNewAccount(Account account) {
		Optional<Account> account1=accountRepository.findById(account.getUserName());
		if(account1.isPresent()==true){
			log.error("conflix username");
			throw new ConflixIdException("conflix username");
		}
		return  accountRepository.save(account);
	}

	@Override
	public Account updateAccount(String id, Account account) {
		Account accountFind=accountRepository.findById(id).orElseThrow(()->new NotFoundException("Not found id "+id));
		if(account.getPassword()!=null){
			accountFind.setPassword(passwordEncoder.encode(account.getPassword()));
		}
		accountFind.setAccType(account.isAccType());
		return accountRepository.save(accountFind);
	}
}
