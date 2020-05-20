package com.springframework.projectshoptoy.service;

import com.springframework.projectshoptoy.api.commandObject.ProductCommand;
import com.springframework.projectshoptoy.api.domain.Category;
import com.springframework.projectshoptoy.api.domain.Customer;
import com.springframework.projectshoptoy.api.domain.Order;
import com.springframework.projectshoptoy.api.domain.OrderDetails;
import com.springframework.projectshoptoy.api.domain.Product;
import com.springframework.projectshoptoy.api.domain.Supplier;
import com.springframework.projectshoptoy.api.mapper.ProductMapper;
import com.springframework.projectshoptoy.dao.MyEntityManager;
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

	@Autowired
	private ProductMapper proMapper;


	public ProductServiceImpl() {
		myEntityManager=new MyEntityManager();
	}

	@Override
	public Set<ProductCommand> getListProduct() {
		log.debug("get list Product");
		Set<ProductCommand> productSet=new HashSet<>();
		myEntityManager.getAllData(new Product()).forEach(t->{
			productSet.add(proMapper.productToProductCommand(t));
		});;
		return productSet;
	}

	@Override
	public boolean deleteProduct(String idProduct) {
		if(idProduct==null) {
			throw new NotFoundException("idProduct can't be null");
		}
		Product product=proMapper.productCommandToProduct(findProductByID(idProduct));
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
	public ProductCommand findProductByID(String id) {
		return proMapper.productToProductCommand((Product) myEntityManager.findById(new Product(), id).orElseThrow(()->new NotFoundException("can find id Product"+id)));
	}

	@Override
	public ProductCommand createNewProduct(Product product) {
		if(product.getProductID()!=null){
			if(myEntityManager.findById(new Product(),product.getProductID()).isPresent()) {
				throw new ConflixIdException("product id had exists");
			}
		}
		if(product.getCategoryID()==null || product.getCategoryID().getCategoryID()==null){
			throw  new NotFoundException("ID category can't be null");
		}

		Category category=(Category) myEntityManager.findById(new Category(), product.getCategoryID().getCategoryID()).orElseThrow(()->new NotFoundException("can find id category"+product.getCategoryID().getCategoryID()));
		product.setCategoryID(category);
		if(product.getSupplierID()==null || product.getSupplierID().getSupplierID()==null){
			throw new NotFoundException("ID supplier can't be null");
		}
		System.out.println(product.getSupplierID().getSupplierID());
		Supplier supplier=(Supplier) myEntityManager.findById(new Supplier(), product.getSupplierID().getSupplierID())
				.orElseThrow(()->new NotFoundException("can find id category"+product.getSupplierID().getSupplierID()));
		product.setSupplierID(supplier);
		product.setProductID("PT"+ObjectId.get().toString());
		System.out.println(product);
		try {
			boolean result=myEntityManager.addT(product,product.getProductID());
			if(result==false) {
				return null;
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return proMapper.productToProductCommand(product);
	}

	@Override
	public ProductCommand updateProduct(String id, Product product) {
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
		return proMapper.productToProductCommand(myEntityManager.updateT(productFind, productFind.getProductID()).get());
	}


}
