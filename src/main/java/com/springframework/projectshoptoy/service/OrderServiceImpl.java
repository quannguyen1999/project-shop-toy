package com.springframework.projectshoptoy.service;

import com.springframework.projectshoptoy.commandObject.OrderCommand;
import com.springframework.projectshoptoy.dao.MyEntityManager;
import com.springframework.projectshoptoy.domain.Customer;
import com.springframework.projectshoptoy.domain.Order;
import com.springframework.projectshoptoy.domain.OrderDetails;
import com.springframework.projectshoptoy.domain.Product;
import com.springframework.projectshoptoy.exception.ConflixIdException;
import com.springframework.projectshoptoy.exception.NotFoundException;
import com.springframework.projectshoptoy.mapper.OrderMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import javax.persistence.EntityTransaction;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService{
	@Autowired
	private MyEntityManager myEntityManager;
	
	@Autowired
	private OrderMapper orderMapper;

	@Override
	public Set<OrderCommand> getListOrder() {
		log.debug("get list orders");
		Set<OrderCommand> orderSet=new HashSet<>();
		myEntityManager.getAllData(new Order()).forEach(t->{
			orderSet.add(orderMapper.orderToOrderCommand(t));
		});
		return orderSet;
	}


	@Override
	public Set<OrderDetails> getListOrderDetails(String orderID) {
		log.debug("get list orderDetails");
		List<Order> orderFind=myEntityManager.query("db.orders.find({'orderID':'"+orderID+"'})",new Order());
		if(orderFind.size()<=0) {
			throw new NotFoundException("not found order by orderID "+orderID);
		}
		Set<OrderDetails> orderDetailsSet=(Set<OrderDetails>) orderFind.get(0).getOrderDetails();
		return orderDetailsSet;
	}

	@Override
	public boolean deleteOrder(String idOrder) {
		if(idOrder==null){
			throw  new NotFoundException("id order can't be null");
		}
		Order order=findOrderById(idOrder);
		if(order==null) {
			throw new NotFoundException("Not found that order id");
		}
		return myEntityManager.deleteT(order, idOrder);
	}

	@Override
	public boolean deleteOrderDetails(String orderID, String idProduct) {
		if(orderID==null){
			throw  new NotFoundException("id order can't be null");
		}
		Order order=findOrderById(orderID);
		if(idProduct==null) {
			throw  new NotFoundException("idProduct can't be null");
		}
		for(int i=0;i<order.getOrderDetails().size();i++) {
			if(order.getOrderDetails().get(i).getProduct().getProductID().equals(idProduct)) {
				order.getOrderDetails().remove(order.getOrderDetails().get(i));
			}
		}
		Order orderUpdate=updateOrder(orderID, order);
		if(orderUpdate.getOrderDetails().size()<=0) {
			deleteOrder(orderUpdate.getOrderID());
		}
		return true;
	}

	@Override
	public Order findOrderById(String id) {
		return (Order) myEntityManager.findById(new Order(), id).orElseThrow(()->new NotFoundException("can find id Order"+id));
	}

	@Override
	public Order createNewOrder(Order order) {
		Customer customer=null;
		if(order.getCustomer()!=null){
			customer=(Customer) myEntityManager.findById(new Customer(), order.getCustomer().getCustomerID()).orElseThrow(()->new NotFoundException("can find id customer"+order.getCustomer().getCustomerID()));
		}else{
			throw new NotFoundException("customerID can't be null");
		}
		if(order.getOrderID()!=null){
			if(myEntityManager.findById(new Order(),order.getOrderID()).isPresent()) {
				throw new ConflixIdException("order id had exists");
			}
		}
		order.setOrderID("OD"+ObjectId.get().toString());
		order.setCustomer(customer);
		order.setOrderDate(LocalDate.now());
		if(order.getOrderDetails().size()<=0) {
			throw new ConflixIdException("please insert minium 1 orderDetail in this order");
		}
		order.getOrderDetails().forEach(orderDetails->{
			int total=0;
			if(orderDetails.getProduct()==null || orderDetails.getProduct().getProductID()==null) {
				throw new NotFoundException("product can't be null");
			}
			Product product=(Product) myEntityManager.findById(new Product(), orderDetails.getProduct().getProductID()).orElseThrow(()->new NotFoundException("can find id product"+orderDetails.getProduct().getProductID()));
			if(product.getQuantityInStock()<orderDetails.getQuanity()){
				throw  new ConflixIdException("product quantity just have :"+product.getQuantityInStock());
			}
			product.setQuantityInStock(product.getQuantityInStock()-orderDetails.getQuanity());
			int u=(int)product.getUnitPrice();
			if(Math.round(orderDetails.getDiscount())==0){
				total=orderDetails.getQuanity()*u;
			}else{
				total=orderDetails.getQuanity()*u-((orderDetails.getQuanity()*u)*Math.round(orderDetails.getDiscount())/100);
			}
			orderDetails.setProduct(product);
			orderDetails.setTotalAmount(total);

			//			product.setQuantityInStock(product.getQuantityInStock()-orderDetails.getQuanity());
			System.out.println(product);
			System.out.println(myEntityManager.updateT(product	, product.getProductID()).get());
		});

		boolean result=myEntityManager.addT(order,order.getOrderID());
		if(result==false) {
			return null;
		}
		return  order;
	}

	@Override
	public Order updateOrder(String id, Order order) {
		Order orderFind=findOrderById(id);
		if(orderFind==null) {
			throw new NotFoundException("Not found that orderID");
		}

		if(order.getOrderDate()!=null){
			orderFind.setOrderDate(order.getOrderDate());
		}
		if(order.getShipCity()!=null){
			orderFind.setShipCity(order.getShipCity());
		}
		if(order.getShippedDate()!=null){
			orderFind.setShippedDate(order.getShippedDate());
		}
		if(order.getShipRegion()!=null){
			orderFind.setShipRegion(order.getShipRegion());
		}

		return myEntityManager.updateT(orderFind, orderFind.getOrderID()).get();
	}

	@Override
	public OrderDetails updateOrderDetails(String id, OrderDetails orderDetails) {
		if(id==null){
			throw new NotFoundException("orderID can't be null");
		}
		if(orderDetails.getProduct()==null) {
			throw new NotFoundException("product can't be null");
		}
		OrderDetails oddtUpdate=createNewOrderDetail(id, orderDetails);
		if(oddtUpdate==null) {
			throw new ConflixIdException("can't update that orderDetails id");
		}
		return oddtUpdate;
	}

	boolean resultTypeFound=false;
	@Override
	public OrderDetails createNewOrderDetail(String orderID, OrderDetails orderDetails) {
		int total=0;
		if(orderID==null){
			throw  new NotFoundException("orderID can't be null");
		}
		Order order=findOrderById(orderID);
		if(order==null) {
			throw new NotFoundException("Not found order id "+orderID);
		}
		if(orderDetails.getProduct().getProductID()==null){
			throw  new NotFoundException("id product can't be null");
		}
		Product product=(Product) myEntityManager.findById(new Product(), orderDetails.getProduct().getProductID()).orElseThrow(()->new NotFoundException("can find id product"+orderDetails.getProduct().getProductID()));
		if(product.getQuantityInStock()<orderDetails.getQuanity()){
			throw new ConflixIdException("product quantity just have :"+product.getQuantityInStock());
		}
		product.setQuantityInStock(product.getQuantityInStock()-orderDetails.getQuanity());
		int u=(int)product.getUnitPrice();
		if(Math.round(orderDetails.getDiscount())==0){
			total=orderDetails.getQuanity()*u;
		}else{
			total=orderDetails.getQuanity()*u-((orderDetails.getQuanity()*u)*Math.round(orderDetails.getDiscount())/100);
		}
		orderDetails.setProduct(product);
		orderDetails.setTotalAmount(total);

		order.getOrderDetails().forEach(t->{
			if(t.getProduct().getProductID().equals(orderDetails.getProduct().getProductID())) {
				t.setQuanity(t.getQuanity()+orderDetails.getQuanity());
				resultTypeFound=true;
			}
		});
		if(resultTypeFound==false) {
			order.getOrderDetails().add(orderDetails);
		}
		//////////////////////////////////////////////////////sửa tạm thời
		EntityTransaction tr=myEntityManager.getEm().getTransaction();
		tr.begin();
		myEntityManager.getEm().merge(product);
		tr.commit();
		//////////////////////////////////////////////////////lỗi
		Order orderUpdate=myEntityManager.updateT(order	, order.getOrderID()).get();
		if(orderUpdate==null) {
			throw new NotFoundException("can't update that orderdetailsID");
		}
		return orderDetails; 	
	}

	@Override
	public Set<OrderCommand> getListOrderByCustomerID(String id) {
		List<Order> listOrderFind= myEntityManager.query("db.orders.find({'customerID':'"+id+"'})",new Order());
		if(listOrderFind.size()<=0) {
			throw new NotFoundException("customer don't have any order");
		}
		Set<OrderCommand> listOrder=new HashSet<OrderCommand>();
		listOrderFind.forEach(t->{
			listOrder.add(orderMapper.orderToOrderCommand(t));
		});
		return listOrder;
	}
}
