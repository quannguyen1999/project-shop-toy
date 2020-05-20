package com.springframework.projectshoptoy.api.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.springframework.projectshoptoy.api.commandObject.ProductCommand;
import com.springframework.projectshoptoy.api.commandObject.SupplierCommand;
import com.springframework.projectshoptoy.api.domain.Product;
import com.springframework.projectshoptoy.api.domain.Supplier;

@Component
public class SupplierMapper {
	public Supplier supplierCommandToSupplier(SupplierCommand supplierCommand) {
		Supplier supplier=new Supplier();
		supplier.setAddress(supplierCommand.getAddress());
		supplier.setCompanyName(supplierCommand.getCompanyName());
		supplier.setPhone(supplierCommand.getPhone());

		List<Product> listProducts=new ArrayList<Product>();
		listProducts.clear();
		ProductMapper productMapper=new ProductMapper();
		if(supplierCommand.getProductCommands()!=null && supplierCommand.getProductCommands().size()>0) {
			supplierCommand.getProductCommands().forEach(t->{
				listProducts.add(productMapper.productCommandToProduct(t));
			});
		}

		supplier.setProducts(listProducts);

		supplier.setSupplierID(supplierCommand.getSupplierID());
		return supplier;
	}

	public SupplierCommand supplierToSupplierCommand(Supplier supplier) {
		SupplierCommand supplierCommand=new SupplierCommand();
		supplierCommand.setAddress(supplier.getAddress());
		supplierCommand.setCompanyName(supplier.getCompanyName());
		supplierCommand.setPhone(supplier.getPhone());

		ProductMapper proMapper=new ProductMapper();
		List<ProductCommand> listCommands=new ArrayList<ProductCommand>();
		listCommands.clear();
		if(supplier.getProducts()!=null && supplier.getProducts().size()>0) {
			supplier.getProducts().forEach(t->{
				listCommands.add(proMapper.productToProductCommand(t));
			});

		}
		supplierCommand.setProductCommands(listCommands);

		supplierCommand.setSupplierID(supplier.getSupplierID());
		return supplierCommand;
	}
}
