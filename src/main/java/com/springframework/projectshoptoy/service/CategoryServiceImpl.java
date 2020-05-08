package com.springframework.projectshoptoy.service;

import com.springframework.projectshoptoy.dao.MyEntityManager;
import com.springframework.projectshoptoy.domain.Account;
import com.springframework.projectshoptoy.domain.Category;
import com.springframework.projectshoptoy.domain.Customer;
import com.springframework.projectshoptoy.domain.Order;
import com.springframework.projectshoptoy.domain.Product;
import com.springframework.projectshoptoy.exception.ConflixIdException;
import com.springframework.projectshoptoy.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class CategoryServiceImpl implements  CategoryService{
	@Autowired
	private MyEntityManager myEntityManager;

	public CategoryServiceImpl() {
		myEntityManager=new MyEntityManager();
	}

	@Override
	public Set<Category> getListCategory() {
		log.debug("get list categorys");
		Set<Category> categorySet=new HashSet<>();
		myEntityManager.getAllData(new Category()).forEach(categorySet::add);;
		return categorySet;
	}

	@Override
	public boolean deleteCategory(String id) {
		if(id==null) {
			throw new NotFoundException("Id category can't be null");
		}
		Category category=findCategoryByID(id);
		List<Product> listProduct=myEntityManager.query("db.products.find({'categoryID':'"+id+"'})",new Product());
		listProduct.forEach(t->{
			
			Set<Order> orderSet=new HashSet<>();
			myEntityManager.getAllData(new Order()).forEach(orderSet::add);
			orderSet.forEach(order->{
				for(int i=0;i<order.getOrderDetails().size();i++) {
					if(order.getOrderDetails().get(i).getProduct().getProductID().equals(t.getProductID())) {
						order.getOrderDetails().remove(order.getOrderDetails().get(i));
					}
				}
				if(order.getOrderDetails().size()<=0) {
					myEntityManager.deleteT(order, order.getOrderID());
				}else {
					myEntityManager.updateT(order,order.getOrderID());
				}
			});
			myEntityManager.deleteT(t, t.getProductID());
		});
		return myEntityManager.deleteT(category,category.getCategoryID());
	}

	@Override
	public Category findCategoryByID(String id) {
		return (Category) myEntityManager.findById(new Category(), id).orElseThrow(()->new NotFoundException("can find id category"+id));
	}

	@Override
	public Category createNewCategory(Category category) {
		if(category.getCategoryID()!=null) {
			if(myEntityManager.findById(new Category(),category.getCategoryID()).isPresent()) {
				throw new ConflixIdException("category id had exists");
			}
		}
		category.setCategoryID("CT"+ObjectId.get().toString());
		boolean result=myEntityManager.addT(category,category.getCategoryID());
		if(result==false) {
			return null;
		};
		return category;
	}

	@Override
	public Category updateCategory(String id, Category category) {
		Category categoryFind=findCategoryByID(id);//categoryRepository.findById(id).orElseThrow(()->new NotFoundException("Not found id "+id));
		if(category.getCategoryName()!=null){
			categoryFind.setCategoryName(category.getCategoryName());
		}
		if(category.getDescription()!=null){
			categoryFind.setDescription(category.getDescription());
		}
		if(category.getPicture()!=null){
			categoryFind.setPicture(category.getPicture());
		}
		return myEntityManager.updateT(categoryFind, categoryFind.getCategoryID()).get();
	}


}
