package com.springframework.projectshoptoy.bootstrap;

import com.springframework.projectshoptoy.domain.Account;
import com.springframework.projectshoptoy.domain.Category;
import com.springframework.projectshoptoy.domain.Customer;
import com.springframework.projectshoptoy.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
@Slf4j
@Component
@RequiredArgsConstructor
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final OrderRepository orderRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        loadData();

        loadCategory();

    }

    private void loadCategory() {
        Category category=new Category();
        category.setCategoryName("abc");
        category.setPicture("1.png");
        category.setDescription("mổ tả");

        log.debug("abc:"+category.toString());

        categoryRepository.save(category);
    }

    private void loadData() {
        Account account=new Account();
        account.setUserName("admin");
        account.setPassword("fuck");

        Account account1=new Account();
        account1.setUserName("employee");
        account1.setPassword("123");
        if(accountRepository.findById(account.getUserName()).isPresent()==false){
            accountRepository.save(account);
        }

        if(accountRepository.findById(account1.getUserName()).isPresent()==false){
            accountRepository.save(account1);
        }

        Customer customer=new Customer();
        customer.setAccount(account);
        customer.setAddress("33/16");
        customer.setCity("usa");
        customer.setEmail("employee@gmail.com");
        customer.setFirstName("kim");
        customer.setLastName("quyen");

        if(customerRepository.findCustomerByUserName(customer.getAccount().getUserName())==null){
            customerRepository.save(customer);
        }

    }
}
