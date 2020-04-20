package com.springframework.projectshoptoy.service;

import com.springframework.projectshoptoy.domain.Order;
import com.springframework.projectshoptoy.exception.ConflixIdException;
import com.springframework.projectshoptoy.exception.NotFoundException;
import com.springframework.projectshoptoy.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
@Service
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;

    @Override
    public Set<Order> getListOrder() {
        log.debug("get list order");
        Set<Order> orderSet=new HashSet<>();
        orderRepository.findAll().iterator().forEachRemaining(orderSet::add);
        return orderSet;
    }

    @Override
    public boolean deleteOrder(String userName) {
        return false;
    }

    @Override
    public Order findOrderById(String id) {
        return orderRepository.findById(id).orElseThrow(()->new NotFoundException("can't find id "+id));
    }

    @Override
    public Order createNewOrder(Order order) {
        Optional<Order> order1=orderRepository.findById(order.getOrderID());
        if(order1.isPresent()==true){
            log.error("conflix id");
            throw new ConflixIdException("conflix id");
        }
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
}
