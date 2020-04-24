package com.springframework.projectshoptoy.service;

import com.springframework.projectshoptoy.domain.Category;
import com.springframework.projectshoptoy.domain.Order;
import com.springframework.projectshoptoy.domain.Product;
import com.springframework.projectshoptoy.domain.Supplier;
import com.springframework.projectshoptoy.exception.ConflixIdException;
import com.springframework.projectshoptoy.exception.NotFoundException;
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
public class SupplierServiceImpl implements   SupplierService{
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Override
    public Set<Supplier> getListSupplier() {
        log.debug("get list supplier");
        Set<Supplier> supplierSet=new HashSet<>();
        supplierRepository.findAll().iterator().forEachRemaining(supplierSet::add);
        return supplierSet;
    }

    @Override
    public boolean deleteSupplier(String idSupplier) {
    	Supplier supplierFind=supplierRepository.findById(idSupplier)
    			.orElseThrow(()->new NotFoundException("category not found id "+idSupplier));
    	productRepository.listAllProductByIdCategory(idSupplier).forEach(product->{
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
    	supplierRepository.delete(supplierFind);
        return true;
    }

    @Override
    public Supplier findSupplierById(String Id) {
        return supplierRepository.findById(Id).orElseThrow(() -> new NotFoundException("can find id " + Id));
    }

    @Override
    public Supplier createNewSupplier(Supplier supplier) {
        if(supplier.getSupplierID()!=null){
            Optional<Supplier> supplier1=supplierRepository.findById(supplier.getSupplierID());
            if(supplier1.isPresent()==true){
                log.error("conflix id");
                throw new ConflixIdException("conflix id");
            }
        }
        supplier.setSupplierID("SL"+ObjectId.get().toString());
        return  supplierRepository.save(supplier);
    }

    @Override
    public Supplier updateSupplier(String id, Supplier supplier) {
        Supplier supplierFind=supplierRepository.findById(id).orElseThrow(()->new NotFoundException("Not found id "+id));
        if(supplier.getAddress()!=null){
            supplierFind.setAddress(supplier.getAddress());
        }
        if(supplier.getCompanyName()!=null){
            supplierFind.setCompanyName(supplier.getCompanyName());
        }
        if(supplier.getPhone()!=null){
            supplierFind.setPhone(supplier.getPhone());
        }
        return supplierRepository.save(supplierFind);
    }
}
