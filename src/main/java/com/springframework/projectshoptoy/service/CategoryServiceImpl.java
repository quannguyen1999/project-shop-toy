package com.springframework.projectshoptoy.service;

import com.springframework.projectshoptoy.domain.Account;
import com.springframework.projectshoptoy.domain.Category;
import com.springframework.projectshoptoy.domain.Order;
import com.springframework.projectshoptoy.domain.Product;
import com.springframework.projectshoptoy.exception.ConflixIdException;
import com.springframework.projectshoptoy.exception.NotFoundException;
import com.springframework.projectshoptoy.repositories.CategoryRepository;
import com.springframework.projectshoptoy.repositories.OrderDetailRepository;
import com.springframework.projectshoptoy.repositories.OrderRepository;
import com.springframework.projectshoptoy.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
@Service
public class CategoryServiceImpl implements  CategoryService{
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Override
    public Set<Category> getListCategory() {
        log.debug("get list category");
        Set<Category> categorySet=new HashSet<>();
        categoryRepository.findAll().iterator().forEachRemaining(categorySet::add);
        return categorySet;
    }

    @Override
    public boolean deleteCategory(String id) {
    	Category cateogryFind=categoryRepository.findById(id)
    			.orElseThrow(()->new NotFoundException("category not found id "+id));
    	productRepository.listAllProductByIdCategory(id).forEach(product->{
    		Optional<Product> productFind=productRepository.findById(product.getProductID());
    		if(productFind.isPresent()==true) {
    			List<String> listIDOrder=new ArrayList<String>();
    	    	orderDetailRepository.listAllOrderDetailsByProductId(productFind.get().getProductID()).forEach(orderDetails->{
    	    		if(listIDOrder.contains(orderDetails.getOrder().getOrderID())==false) {
    	    			listIDOrder.add(orderDetails.getOrder().getOrderID());
    	    		}
    	    		orderDetailRepository.delete(orderDetails);
    	    	});
    	    	listIDOrder.forEach(idOrder->{
    	    		Optional<Order> order=orderRepository.findById(idOrder);
    	    		if(order.isPresent()==true) {
    	    			int totalOrder=orderDetailRepository.listAllOrderDetailsByIdOrder(order.get().getOrderID())
    	    			.size();
    	    			if(totalOrder<=0) {
    	    				orderRepository.delete(order.get());
    	    			}
    	    		}
    	    	});
    	    	productRepository.delete(product);
    		}
    	});
    	categoryRepository.delete(cateogryFind);
        return true;
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
        category.setCategoryID("CT"+ObjectId.get().toString());
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
