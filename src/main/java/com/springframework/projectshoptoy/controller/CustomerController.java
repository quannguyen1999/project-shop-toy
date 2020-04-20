package com.springframework.projectshoptoy.controller;

import com.springframework.projectshoptoy.domain.Account;
import com.springframework.projectshoptoy.domain.Customer;
import com.springframework.projectshoptoy.service.AccountService;
import com.springframework.projectshoptoy.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(CustomerController.BASE_URL)
@RestController
public class CustomerController {
    public final static String BASE_URL="/api/customers";
    private final CustomerService customerService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Set<Customer> getListCustomers(){
        return customerService.getCustomers();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String id){
       log.debug("deleting id:"+id);
       boolean result=customerService.deleteCustomerById(id);
       if(result==false){
           return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
       }
       return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("/userName/{id}")
    public ResponseEntity<Customer> findCustomerByUserName(@PathVariable String id) throws Exception {
        return new ResponseEntity<Customer>(customerService.findCustomerByUserName(id),HttpStatus.OK);
    }


}
