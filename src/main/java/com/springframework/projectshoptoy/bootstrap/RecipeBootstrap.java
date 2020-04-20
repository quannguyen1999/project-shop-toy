package com.springframework.projectshoptoy.bootstrap;

import com.springframework.projectshoptoy.domain.Account;
import com.springframework.projectshoptoy.domain.Customer;
import com.springframework.projectshoptoy.repositories.AccountRepository;
import com.springframework.projectshoptoy.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    public RecipeBootstrap(AccountRepository accountRepository, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Account account=new Account();
        account.setUserName("admin");
        account.setPassword("fuck");


        Account account1=new Account();
        account1.setUserName("employee");
        account1.setPassword("123");

        accountRepository.save(account);
        accountRepository.save(account1);

        Customer customer=new Customer();
        customer.setAccount(account);
        customer.setAddress("33/16");
        customer.setCity("usa");
        customer.setEmail("employee@gmail.com");
        customer.setFirstName("kim");
        customer.setLastName("quyen");

        customerRepository.save(customer);

    }
}
