package com.springframework.projectshoptoy.service;

import com.springframework.projectshoptoy.domain.Account;
import com.springframework.projectshoptoy.domain.Customer;
import com.springframework.projectshoptoy.exception.ConflixIdException;
import com.springframework.projectshoptoy.exception.NotFoundException;
import com.springframework.projectshoptoy.repositories.AccountRepository;
import com.springframework.projectshoptoy.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
@RequiredArgsConstructor
@Slf4j
@Service
public class AccountServiceImpl implements  AccountService{
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

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
            log.error("conflix id");
            throw new ConflixIdException("conflix id");
        }
        return  accountRepository.save(account);
    }

    @Override
    public Account updateAccount(String id, Account account) {
        Account accountFind=accountRepository.findById(id).orElseThrow(()->new NotFoundException("Not found id "+id));
        if(account.getPassword()!=null){
            accountFind.setPassword(account.getPassword());
        }
        accountFind.setAccType(account.isAccType());
        return accountRepository.save(accountFind);
    }
}
