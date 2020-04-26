package com.springframework.projectshoptoy.service;

import com.springframework.projectshoptoy.domain.Category;
import com.springframework.projectshoptoy.domain.Order;
import com.springframework.projectshoptoy.domain.OrderDetails;
import com.springframework.projectshoptoy.domain.Product;
import com.springframework.projectshoptoy.domain.Supplier;
import com.springframework.projectshoptoy.exception.ConflixIdException;
import com.springframework.projectshoptoy.exception.NotFoundException;
import com.springframework.projectshoptoy.repositories.CategoryRepository;
import com.springframework.projectshoptoy.repositories.OrderDetailRepository;
import com.springframework.projectshoptoy.repositories.OrderRepository;
import com.springframework.projectshoptoy.repositories.ProductRepository;
import com.springframework.projectshoptoy.repositories.SupplierRepository;
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
public class ProductServiceImpl implements ProductService {
	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;
	private final SupplierRepository supplierRepository;
	private final OrderDetailRepository orderDetailRepository;
	private final OrderRepository orderRepository;

	@Override
	public Set<Product> getListProduct() {
		log.debug("get list product");
		Set<Product> productSet=new HashSet<>();
		productRepository.findAll().iterator().forEachRemaining(productSet::add);
		return productSet;
	}

	@Override
	public boolean deleteProduct(String idProduct) {
		Product product=productRepository.findById(idProduct)
				.orElseThrow(()->new NotFoundException("product not found id "+idProduct));
		//list danh sách by productID
		orderDetailRepository.listAllOrderDetailsByProductId(idProduct)
		//get danh sách	orderDetails
		.stream()
		//xóa order Details trước và trả về kiểu String:orderDetailsID
		.map(orderDetails->{
			orderDetailRepository.delete(orderDetails);
			return orderDetails.getOrder().getOrderID();
		})
		//id nào trùng thỉ bỏ bớt ra 1 cái
		.distinct()
		//list ra những OrderDetails ra
		.forEach(orderID->{
			System.out.println(orderID);
			//tìm Order
			Optional<Order> order=orderRepository.findById(orderID);
			//Nếu Order có
			if(order.isPresent()==true) {
				//tính tổng kích cỡ order
				int totalOrder=orderDetailRepository
						.listAllOrderDetailsByIdOrder(order.get().getOrderID())
						.size();
				System.out.println(totalOrder);
				//nếu order dưới <=0 thì xóa,(vì không còn order nào)
				if(totalOrder<=0) {
					orderRepository.delete(order.get());
				}
			}
		});
		productRepository.delete(product);
		return true;
	}

	@Override
	public Product findProductByID(String id) {
		return productRepository.findById(id).orElseThrow(()->new NotFoundException("can find id "+id));
	}

	@Override
	public Product createNewProduct(Product product) {
		if(product.getProductID()!=null){
			Optional<Product> productFind=productRepository.findById(product.getProductID());
			if(productFind!=null){
				throw  new ConflixIdException("conflix id product id "+productFind.get().getProductID());
			}
		}
		if(product.getCategory()==null || product.getCategory().getCategoryID()==null){
			throw  new NotFoundException("ID category can't be null");
		}
		Category category=categoryRepository.findById(product.getCategory().getCategoryID()).orElseThrow(()->new NotFoundException("Not found id category "+product.getCategory().getCategoryID()));
		product.setCategory(category);
		if(product.getSupplier()==null || product.getSupplier().getSupplierID()==null){
			throw new NotFoundException("ID supplier can't be null");
		}
		Supplier supplier=supplierRepository.findById(product.getSupplier().getSupplierID()).orElseThrow(()->new NotFoundException("Not found id supplier "+product.getSupplier().getSupplierID()));
		product.setSupplier(supplier);
		product.setProductID("PT"+ObjectId.get().toString());
		return productRepository.save(product);
	}

	@Override
	public Product updateProduct(String id, Product product) {
		Product productFind=productRepository.findById(id).orElseThrow(()->new NotFoundException("Not found id "+id));
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
		return productRepository.save(productFind);
	}
}
