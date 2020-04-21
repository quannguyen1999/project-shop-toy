package com.springframework.projectshoptoy.repositories;

import com.springframework.projectshoptoy.domain.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account,String> {
}
