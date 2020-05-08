package com.springframework.projectshoptoy.service;

import com.springframework.projectshoptoy.dao.MyEntityManager;
import com.springframework.projectshoptoy.domain.Category;
import com.springframework.projectshoptoy.domain.Customer;
import com.springframework.projectshoptoy.domain.Order;
import com.springframework.projectshoptoy.domain.OrderDetails;
import com.springframework.projectshoptoy.domain.Product;
import com.springframework.projectshoptoy.domain.Supplier;
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
public class ProductServiceImpl implements ProductService {
	@Autowired
	private MyEntityManager myEntityManager;
	
	public ProductServiceImpl() {
		myEntityManager=new MyEntityManager();
	}

	@Override
	public Set<Product> getListProduct() {
		log.debug("get list Product");
		Set<Product> productSet=new HashSet<>();
		myEntityManager.getAllData(new Product()).forEach(productSet::add);;
		return productSet;
	}

	@Override
	public boolean deleteProduct(String idProduct) {
		if(idProduct==null) {
			throw new NotFoundException("idProduct can't be null");
		}
		Product product=findProductByID(idProduct);
		Set<Order> orderSet=new HashSet<>();
		
		myEntityManager.getAllData(new Order()).forEach(orderSet::add);
		orderSet.forEach(order->{
			for(int i=0;i<order.getOrderDetails().size();i++) {
				if(order.getOrderDetails().get(i).getProduct().getProductID().equals(idProduct)) {
					order.getOrderDetails().remove(order.getOrderDetails().get(i));
				}
			}
			if(order.getOrderDetails().size()<=0) {
				myEntityManager.deleteT(order, order.getOrderID());
			}else {
				myEntityManager.updateT(order,order.getOrderID());
			}
		});
		return myEntityManager.deleteT(product, idProduct);
	}

	@Override
	public Product findProductByID(String id) {
		return (Product) myEntityManager.findById(new Product(), id).orElseThrow(()->new NotFoundException("can find id Product"+id));
	}

	@Override
	public Product createNewProduct(Product product) {
		if(product.getProductID()!=null){
			if(myEntityManager.findById(new Product(),product.getProductID()).isPresent()) {
				throw new ConflixIdException("product id had exists");
			}
		}
		if(product.getCategory()==null || product.getCategory().getCategoryID()==null){
			throw  new NotFoundException("ID category can't be null");
		}
		
		Category category=(Category) myEntityManager.findById(new Category(), product.getCategory().getCategoryID()).orElseThrow(()->new NotFoundException("can find id category"+product.getCategory().getCategoryID()));
		product.setCategory(category);
		if(product.getSupplier()==null || product.getSupplier().getSupplierID()==null){
			throw new NotFoundException("ID supplier can't be null");
		}
		Supplier supplier=(Supplier) myEntityManager.findById(new Supplier(), product.getSupplier().getSupplierID())
				.orElseThrow(()->new NotFoundException("can find id category"+product.getSupplier().getSupplierID()));
		product.setSupplier(supplier);
		product.setProductID("PT"+ObjectId.get().toString());
		boolean result=myEntityManager.addT(product,product.getProductID());
		if(result==false) {
			return null;
		}
		return product;
	}

	@Override
	public Product updateProduct(String id, Product product) {
		Product productFind=(Product) myEntityManager.findById(new Product(), product.getProductID()).orElseThrow(()->new NotFoundException("can find id Product"+product.getProductID()));
		if(product.isDiscontinued()==true){
			productFind.setDiscontinued(product.isDiscontinued());
		}
		if(product.getMoTa()!=null){
			productFind.setMoTa(product.getMoTa());
		}
		if(product.getProductName()!=null){
			productFind.setProductName(product.getProductName());
		}
		if(product.getQuantityInStock()!=productFind.getQuantityInStock()){
			productFind.setQuantityInStock(product.getQuantityInStock());
		}
		if(product.getUnitPrice()!=productFind.getUnitPrice()){
			productFind.setUnitPrice(product.getUnitPrice());
		}
		return myEntityManager.updateT(productFind, productFind.getProductID()).get();
	}

	
}
