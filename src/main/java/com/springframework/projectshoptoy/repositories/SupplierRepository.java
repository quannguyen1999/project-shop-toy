package com.springframework.projectshoptoy.repositories;

import com.springframework.projectshoptoy.domain.Account;
import com.springframework.projectshoptoy.domain.Supplier;
import org.springframework.data.repository.CrudRepository;

public interface SupplierRepository extends CrudRepository<Supplier,String> {

}
