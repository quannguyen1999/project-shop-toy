package com.springframework.projectshoptoy.repositories;

import com.springframework.projectshoptoy.domain.Product;

import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product,String> {
	@Query(value = "{ 'category.$id' : ?0 }")
	List<Product> listAllProductByIdCategory(String categoryID);
	
	@Query(value = "{ 'supplier.$id' : ?0 }")
	List<Product> listAllProductByIdSupplier(String supplierID);
}
