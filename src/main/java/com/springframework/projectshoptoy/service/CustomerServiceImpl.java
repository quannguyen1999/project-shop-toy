package com.springframework.projectshoptoy.service;

import com.springframework.projectshoptoy.api.commandObject.CustomerCommand;
import com.springframework.projectshoptoy.api.domain.Account;
import com.springframework.projectshoptoy.api.domain.Customer;
import com.springframework.projectshoptoy.api.domain.Order;
import com.springframework.projectshoptoy.api.mapper.CustomerMapper;
import com.springframework.projectshoptoy.dao.MyEntityManager;
import com.springframework.projectshoptoy.exception.ConflixIdException;
import com.springframework.projectshoptoy.exception.NotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@Slf4j
@Service
public class CustomerServiceImpl implements  CustomerService{
	
	@Autowired
	private CustomerMapper customerMapper;
	
	@Autowired
	private MyEntityManager myEntityManager;

	@Override
	public Set<CustomerCommand> getCustomers() {
		log.debug("get list accounts");
		Set<CustomerCommand> customerSet=new HashSet<>();
		myEntityManager.getAllData(new Customer()).forEach(t->{
			System.out.println(t.getAddress());
			customerSet.add(customerMapper.customerToCustomerCommand(t));
		});
		return customerSet;
	}

	@Override
	public boolean deleteCustomerById(String id) {
		if(id==null){
			throw new NotFoundException("id can't be null");
		}
		Customer customr=customerMapper.customerCommandToCustomer(findCustomerById(id));
		return myEntityManager.deleteT(customr, customr.getCustomerID());
	}

	@Override
	public CustomerCommand findCustomerByUserName(String userName){
		List<Customer> listCustomer= myEntityManager.query("db.customers.find({'username':'"+userName+"'})",new Customer());
		if(listCustomer.size()<=0) {
			throw new NotFoundException("not found customer by username "+userName);
		}
		return customerMapper.customerToCustomerCommand(listCustomer.get(0));
	}

	@Override
	public CustomerCommand findCustomerById(String id) {
		return customerMapper.customerToCustomerCommand((Customer) myEntityManager.findById(new Customer(), id).orElseThrow(()->new NotFoundException("can find id customer"+id)));
	}

	@Override
	public CustomerCommand createNewCustomer(String userName,Customer customer) {
		if(customer.getCustomerID()!=null) {
			if(myEntityManager.findById(new Customer(),customer.getCustomerID()).isPresent()) {
				throw new ConflixIdException("customer id had exists");
			}
		}
		Optional<Object> obAccount=myEntityManager.findById(new Account(),userName);
		Account account=new Account();
		if(obAccount==null) {
			throw new NotFoundException("not found userName "+userName);
		}
		account=(Account) obAccount.get();
		List<Customer> customer2= myEntityManager.query("db.customers.find({'username':'"+userName+"'})");
		if(customer2.size()>0) {
			throw  new ConflixIdException("userName has exists account customer:please provide different userName");
		}
		customer.setCustomerID("CU"+ObjectId.get().toString());
		customer.setAccount(account);
		boolean result=myEntityManager.addT(customer,customer.getCustomerID());
		if(result==false) {
			return null;
		}
		return customerMapper.customerToCustomerCommand(customer);
	}

	@Override
	public CustomerCommand updateCustomer(String username, Customer customer) {
		Customer customer1=customerMapper.customerCommandToCustomer(findCustomerByUserName(username));
		if(customer1==null) {
			throw new NotFoundException("Not found that username");
		}
		if(customer.getAddress()!=null){
			customer1.setAddress(customer.getAddress());
		}
		if(customer.getCity()!=null){
			customer1.setCity(customer.getCity());
		}
//		if(customer.getEmail()!=null){
//			customer1.setEmail(customer.getEmail());
//		}
		if(customer.getFirstName()!=null){
			customer1.setFirstName(customer.getFirstName());
		}
		if(customer.getLastName()!=null){
			customer1.setLastName(customer.getLastName());
		}
		return customerMapper.customerToCustomerCommand(myEntityManager.updateT(customer1, customer1.getCustomerID()).get());
	}


}
