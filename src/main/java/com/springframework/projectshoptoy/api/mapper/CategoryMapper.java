package com.springframework.projectshoptoy.api.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.springframework.projectshoptoy.api.commandObject.CategoryCommand;
import com.springframework.projectshoptoy.api.commandObject.ProductCommand;
import com.springframework.projectshoptoy.api.domain.Category;
import com.springframework.projectshoptoy.api.domain.Product;
//Chuyển các object thành object Command để đảm bảo dữ liệu không bị vòng lặp trong bi-direction
@Component
public class CategoryMapper {
	public CategoryCommand categoryToCategoyCommand(Category category) {
		CategoryCommand categoryCommand=new CategoryCommand();
		categoryCommand.setCategoryID(category.getCategoryID());
		categoryCommand.setCategoryName(category.getCategoryName());
		categoryCommand.setDescription(category.getDescription());
		categoryCommand.setPicture(category.getPicture());

		ProductMapper productMapper=new ProductMapper();
		List<ProductCommand> productCommands=new ArrayList<ProductCommand>();
		productCommands.clear();
		if(category.getProducts()!=null && category.getProducts().size()>0) {
			category.getProducts().forEach(t->{
				productCommands.add(productMapper.productToProductCommand(t));
			});

		}
		categoryCommand.setProductCommands(productCommands);
		return categoryCommand;
	}

	public Category categoryCommandToCategory(CategoryCommand categoryCommand) {
		Category category=new Category();
		category.setCategoryID(categoryCommand.getCategoryID());
		category.setCategoryName(categoryCommand.getCategoryName());
		category.setDescription(categoryCommand.getDescription());
		category.setPicture(categoryCommand.getPicture());

		ProductMapper productMapper=new ProductMapper();
		List<Product> listProducts=new ArrayList<Product>();
		listProducts.clear();
		if(categoryCommand.getProductCommands()!=null && categoryCommand.getProductCommands().size()>0) {
			categoryCommand.getProductCommands().forEach(t->{
				listProducts.add(productMapper.productCommandToProduct(t));
			});
		}
		
		category.setProducts(listProducts);
		
		
		return category;
	}

}
