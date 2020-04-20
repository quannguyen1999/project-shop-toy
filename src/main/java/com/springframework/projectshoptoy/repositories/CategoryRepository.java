package com.springframework.projectshoptoy.repositories;

import com.springframework.projectshoptoy.domain.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category,String> {

}
