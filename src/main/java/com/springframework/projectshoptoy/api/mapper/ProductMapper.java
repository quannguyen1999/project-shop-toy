package com.springframework.projectshoptoy.api.mapper;

import org.springframework.stereotype.Component;
import com.springframework.projectshoptoy.api.commandObject.ProductCommand;
import com.springframework.projectshoptoy.api.domain.Category;
import com.springframework.projectshoptoy.api.domain.Product;
import com.springframework.projectshoptoy.api.domain.Supplier;
//Chuyển các object thành object Command để đảm bảo dữ liệu không bị vòng lặp trong bi-direction
@Component
public class ProductMapper {
	public ProductCommand productToProductCommand(Product product) {
		ProductCommand productCommand=new ProductCommand();
		productCommand.setCategoryID(product.getCategoryID().getCategoryID());
		productCommand.setDiscontinued(product.isDiscontinued());
		productCommand.setMoTa(product.getMoTa());
		productCommand.setProductID(product.getProductID());
		productCommand.setProductName(product.getProductName());
		productCommand.setQuantityInStock(product.getQuantityInStock());
		productCommand.setSupplierID(product.getSupplierID().getSupplierID());
		productCommand.setUnitPrice(product.getUnitPrice());
		return productCommand;
	}
	public Product productCommandToProduct(ProductCommand productCommand) {
		Product product=new Product();
		Category category=new Category();
		category.setCategoryID(productCommand.getCategoryID());
		product.setCategoryID(category);
		product.setDiscontinued(productCommand.isDiscontinued());
		product.setMoTa(productCommand.getMoTa());
		product.setProductID(productCommand.getProductID());
		product.setProductName(productCommand.getProductName());
		product.setQuantityInStock(productCommand.getQuantityInStock());
		Supplier supplier=new Supplier();
		supplier.setSupplierID(productCommand.getSupplierID());
		product.setSupplierID(supplier);
		product.setUnitPrice(productCommand.getUnitPrice());
		return product;
	}
}
