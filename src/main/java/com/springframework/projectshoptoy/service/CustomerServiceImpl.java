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

import org.bson.types.ObjectId;
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
    MongoTemplate mongoTemplate;

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailsRepository;

    @Override
    public Set<Customer> getCustomers() {
        Set<Customer> customerSet=new HashSet<>();
        customerRepository.findAll().iterator().forEachRemaining(customerSet::add);
        return customerSet;
    }

    @Override
    public boolean deleteCustomerById(String id) {
        Customer customer=customerRepository.findById(id).orElseThrow(()->new NotFoundException("can't find id "+id));
        List<Order> listOrder=orderRepository.findListOrderByIdCustomer(id);
        listOrder.forEach(order->{
        	orderDetailsRepository.listAllOrderDetailsByIdOrder(order.getOrderID()).stream()
              .map(orderDetailsFind->{
            	  orderDetailsRepository.delete(orderDetailsFind);
                  return null;
              }).collect(Collectors.toList());
        	orderRepository.delete(order);
        });
        accountRepository.delete(customer.getAccount());
        customerRepository.delete(customer);
        return true;
    }

    @Override
    public Customer findCustomerByUserName(String userName) throws  Exception{
        Customer customer= customerRepository.findCustomerByUserName(userName);
        if(customer!=null){
            return customer;
        }
        return null;
    }

    @Override
    public Customer findCustomerById(String id) {
        return customerRepository.findById(id).orElseThrow(()->new NotFoundException("can find id "+id));
    }

    @Override
    public Customer createNewCustomer(String userName,Customer customer) {
    	if(customer.getCustomerID()!=null) {
    		Optional<Customer> customerFind=customerRepository.findById(customer.getCustomerID());
        	if(customerFind.isPresent()==true) {
        		throw new ConflixIdException("customer id had exists");
        	}
    	}
    	Account account=accountRepository.findById(userName).orElseThrow(()->new NotFoundException("not found userName "+userName));
        Customer customer1=customerRepository.findCustomerByUserName(userName);
        if(customer1!=null){
            throw  new ConflixIdException("userName has exists account customer:"+customer1.getCustomerID()+" ,please provide different userName");
        }
        customer.setCustomerID("CU"+ObjectId.get().toString());
        customer.setAccount(account);
        return  customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(String id, Customer customer) {
        Customer customer1=customerRepository.findById(id).orElseThrow(()->new NotFoundException("not found id "+id));
        if(customer.getAddress()!=null){
            customer1.setAddress(customer.getAddress());
        }
        if(customer.getCity()!=null){
            customer1.setCity(customer.getCity());
        }
        if(customer.getEmail()!=null){
            customer1.setEmail(customer.getEmail());
        }
        if(customer.getFirstName()!=null){
            customer1.setFirstName(customer.getFirstName());
        }
        if(customer.getLastName()!=null){
            customer1.setLastName(customer.getLastName());
        }
        return customerRepository.save(customer1);
    }
}
