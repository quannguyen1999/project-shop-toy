package com.springframework.projectshoptoy.service;

import com.springframework.projectshoptoy.api.apiError.CustomRespone;
import com.springframework.projectshoptoy.api.commandObject.CategoryCommand;
import com.springframework.projectshoptoy.api.domain.Category;
import com.springframework.projectshoptoy.api.domain.Order;
import com.springframework.projectshoptoy.api.domain.Product;
import com.springframework.projectshoptoy.api.mapper.CategoryMapper;
import com.springframework.projectshoptoy.dao.MyEntityManager;
import com.springframework.projectshoptoy.exception.ConflixIdException;
import com.springframework.projectshoptoy.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class CategoryServiceImpl implements  CategoryService{
	@Autowired
	private MyEntityManager myEntityManager;
	
	@Autowired
	private CategoryMapper categoryMapper;

	public CategoryServiceImpl() {
		myEntityManager=new MyEntityManager();
	}

	@Override
	public CustomRespone getListCategory() {
		log.debug("get list categorys");
		Set<CategoryCommand> categoryCommandSet=new HashSet<>();
		myEntityManager.getAllData(new Category()).forEach(t->{
			categoryCommandSet.add(categoryMapper.categoryToCategoyCommand(t));
		});;
		return new CustomRespone(10,"danh sách category",categoryCommandSet);
	}

	@Override
	public CustomRespone deleteCategory(String id) {
		if(id==null) {
			return new CustomRespone(1, "id catregory không được rỗng", null);
		}
		CustomRespone customRespone=findCategoryByID(id);
		if(customRespone.getCode()!=9) {
			return customRespone;
		}
		Category category=categoryMapper.categoryCommandToCategory((CategoryCommand)customRespone.getObject());
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
		myEntityManager.deleteT(category,category.getCategoryID());
		return new CustomRespone(7, "Xóa thành công", null);
	}

	@Override
	public CustomRespone findCategoryByID(String id) {
		if(id==null) {
			return new CustomRespone(1, "id category không thể rỗng", null);
		}
		Optional<Object> categoryFind=myEntityManager.findById(new Category(), id);
		if(categoryFind.isEmpty()) {
			return new CustomRespone(3, "không tìm thấy", null);
		}
		CategoryCommand categoryCommand=categoryMapper.categoryToCategoyCommand((Category)categoryFind.get());
		return new CustomRespone(9, "tìm thấy",categoryCommand);
	}

	@Override
	public CustomRespone createNewCategory(Category category) {
		if(category.getCategoryID()!=null) {
			if(myEntityManager.findById(new Category(),category.getCategoryID()).isPresent()) {
				return new CustomRespone(5, "trùng id", null);
			}
		}
		category.setCategoryID("CT"+ObjectId.get().toString());
		boolean result=myEntityManager.addT(category,category.getCategoryID());
		if(result==false) {
			return null;
		};
		
		return new CustomRespone(6,"thêm thành công",categoryMapper.categoryToCategoyCommand(category));
	}

	@Override
	public CustomRespone updateCategory(Category category) {
		if(category.getCategoryID()==null) {
			return new CustomRespone(1, "category id không được null", null);
		}
		CustomRespone customRespone=findCategoryByID(category.getCategoryID());
		if(customRespone.getCode()!=9) {
			return customRespone;
		}
		CategoryCommand categoryCommand=(CategoryCommand)customRespone.getObject();
		Category categoryFind=categoryMapper.categoryCommandToCategory(categoryCommand);//categoryRepository.findById(id).orElseThrow(()->new NotFoundException("Not found id "+id));
		if(category.getCategoryName()!=null){
			categoryFind.setCategoryName(category.getCategoryName());
		}
		if(category.getDescription()!=null){
			categoryFind.setDescription(category.getDescription());
		}
		if(category.getPicture()!=null){
			categoryFind.setPicture(category.getPicture());
		}
		return new CustomRespone(8,"Cập nhập thành công",categoryMapper.categoryToCategoyCommand(myEntityManager.updateT(categoryFind, categoryFind.getCategoryID()).get()));
	}


}
