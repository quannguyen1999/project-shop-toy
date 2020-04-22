package com.springframework.projectshoptoy.service;

import com.springframework.projectshoptoy.domain.Customer;
import com.springframework.projectshoptoy.domain.Order;
import com.springframework.projectshoptoy.domain.OrderDetails;
import com.springframework.projectshoptoy.domain.Product;
import com.springframework.projectshoptoy.exception.ConflixIdException;
import com.springframework.projectshoptoy.exception.NotFoundException;
import com.springframework.projectshoptoy.repositories.CustomerRepository;
import com.springframework.projectshoptoy.repositories.OrderDetailRepository;
import com.springframework.projectshoptoy.repositories.OrderRepository;
import com.springframework.projectshoptoy.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Override
    public Set<Order> getListOrder() {
        log.debug("get list order");
        Set<Order> orderSet=new HashSet<>();
        orderRepository.findAll().iterator().forEachRemaining(orderSet::add);
        return orderSet;
    }
    
   
    @Override
    public Set<OrderDetails> getListOrderDetails(String orderID) {
        log.debug("get list orderDetails");
        Order order=orderRepository.findById(orderID).orElseThrow(()-> new NotFoundException("not found id "+orderID));
        Set<OrderDetails> orderDetailsSet=new HashSet<>();
        orderDetailRepository.listAllOrderDetailsByIdOrder(orderID).iterator().forEachRemaining(orderDetailsSet::add);
        return orderDetailsSet;
    }

    @Override
    public boolean deleteOrder(String idOrder) {
        if(idOrder==null){
            throw  new NotFoundException("id order can't be null");
        }
        Order order=orderRepository.findById(idOrder).orElseThrow(()->new NotFoundException("Can't find id "+idOrder));
        orderDetailRepository.listAllOrderDetailsByIdOrder(idOrder).stream()
                .map(orderDetailsFind->{
                    orderDetailRepository.delete(orderDetailsFind);
                    return null;
                }).collect(Collectors.toList());
        orderRepository.delete(order);
        return true;
    }

    @Override
    public boolean deleteOrderDetails(String orderID, String idOrderDetails) {
        if(orderID==null){
            throw  new NotFoundException("id order can't be null");
        }
        Order order=orderRepository.findById(orderID).orElseThrow(()->new NotFoundException("Can't find id "+orderID));
        if(idOrderDetails==null){
            throw  new NotFoundException("id orderDetails can't be null");
        }
        OrderDetails orderDetails=orderDetailRepository.findById(idOrderDetails).orElseThrow(()->new NotFoundException("Can't find id "+idOrderDetails));
        orderDetailRepository.delete(orderDetails);
        return false;
    }

    @Override
    public Order findOrderById(String id) {
        return orderRepository.findById(id).orElseThrow(()->new NotFoundException("can't find id "+id));
    }

    @Override
    public OrderDetails findOrderDetailById(String orderID, String idOrderDetails) {
        if(orderID==null){
            throw new NotFoundException("orderID can't be null");
        }
        Order order=orderRepository.findById(orderID).orElseThrow(()->new NotFoundException("can't find id order "+orderID));
        if(idOrderDetails==null){
            throw new NotFoundException("idOrderDetails can't be null");
        }
        OrderDetails orderDetails=orderDetailRepository.findById(idOrderDetails).orElseThrow(()->new NotFoundException("can't find id orderDetail "+idOrderDetails));
        return orderDetails;
    }

    @Override
    public Order createNewOrder(Order order) {
        Customer customer=null;
        if(order.getCustomer()!=null){
            customer=customerRepository.findById(order.getCustomer().getCustomerID()).orElseThrow(()->new NotFoundException("Not found customer id "+order.getCustomer().getCustomerID()));
        }else{
            throw new NotFoundException("customerID can't be null");
        }
        if(order.getOrderID()!=null){
            Optional<Order> order1=orderRepository.findById(order.getOrderID());
            if(order1.isPresent()==true){
                log.error("conflix id");
                throw new ConflixIdException("conflix id");
            }
        }
        order.setOrderID("OD"+ObjectId.get().toString());
        order.setCustomer(customer);
        order.setOrderDate(LocalDate.now());
        return  orderRepository.save(order);
    }

    @Override
    public Order updateOrder(String id, Order order) {
        Order orderFind=orderRepository.findById(id).orElseThrow(()->new NotFoundException("Not found id "+id));
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
        return orderRepository.save(orderFind);
    }

    @Override
    public OrderDetails updateOrderDetails(String id, String orderDetailID, OrderDetails orderDetails) {
        if(id==null){
            throw new NotFoundException("orderID can't be null");
        }
        Order order=orderRepository.findById(id).orElseThrow(()->new NotFoundException("can't find id order "+id));
        if(orderDetailID==null){
            throw new NotFoundException("idOrderDetails can't be null");
        }
        OrderDetails orderDetailsFind=orderDetailRepository.findById(orderDetailID).orElseThrow(()->new NotFoundException("can't find id orderDetail "+orderDetailID));
//        if(orderDetails.getQuanity())
        return null;
    }

    @Override
    public OrderDetails createNewOrderDetail(String orderID, OrderDetails orderDetails) {
        int total=0;
        if(orderID==null){
            throw  new NotFoundException("orderID can't be null");
        }
        Order order=orderRepository.findById(orderID).orElseThrow(()->new NotFoundException("Not found order id "+orderID));
        if(orderDetails.getProduct()==null){
            throw  new NotFoundException("id product can't be null");
        }
        Product product=productRepository.findById(orderDetails.getProduct().getProductID()).orElseThrow(()->new NotFoundException(("Not fond product id "+orderDetails.getProduct().getProductID())));
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
        orderDetails.setOrder(order);
        orderDetails.setOrderDetailID("ODDT"+ObjectId.get().toString());
        orderDetails.setTotalAmount(total);
        productRepository.save(product);
        return orderDetailRepository.save(orderDetails);
    }



	@Override
	public Set<Order> getListOrderByCustomerID(String id) {
//		orderRepository.findListOrderByIdCustomer(id)
		// TODO Auto-generated method stub
		return null;
	}
}
