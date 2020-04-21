package com.springframework.projectshoptoy.service;

import com.springframework.projectshoptoy.domain.Account;
import com.springframework.projectshoptoy.domain.Category;
import com.springframework.projectshoptoy.exception.ConflixIdException;
import com.springframework.projectshoptoy.exception.NotFoundException;
import com.springframework.projectshoptoy.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
@Service
public class CategoryServiceImpl implements  CategoryService{
    private final CategoryRepository categoryRepository;

    @Override
    public Set<Category> getListCategory() {
        log.debug("get list category");
        Set<Category> categorySet=new HashSet<>();
        categoryRepository.findAll().iterator().forEachRemaining(categorySet::add);
        return categorySet;
    }

    @Override
    public boolean deleteCategory(String id) {
        return false;
    }

    @Override
    public Category findCategoryByID(String id) {
        return categoryRepository.findById(id).orElseThrow(()->new NotFoundException("can find id "+id));
    }

    @Override
    public Category createNewCategory(Category category) {
        if(category.getCategoryID()!=null){
            Optional<Category> category1=categoryRepository.findById(category.getCategoryID());
            if(category1.isPresent()==true){
                log.error("conflix id");
                throw new ConflixIdException("conflix id "+category1.get().getCategoryID());
            }
        }
        return  categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(String id, Category category) {
        Category categoryFind=categoryRepository.findById(id).orElseThrow(()->new NotFoundException("Not found id "+id));
        if(category.getCategoryName()!=null){
            categoryFind.setCategoryName(category.getCategoryName());
        }
        if(category.getDescription()!=null){
            categoryFind.setDescription(category.getDescription());
        }
        if(category.getPicture()!=null){
            categoryFind.setPicture(category.getPicture());
        }
        return categoryRepository.save(categoryFind);
    }
}
