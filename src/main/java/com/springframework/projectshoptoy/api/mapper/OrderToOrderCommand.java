package com.springframework.projectshoptoy.mapper;


import org.springframework.stereotype.Component;

import com.springframework.projectshoptoy.commandObject.OrderCommand;
@Component
public class OrderToOrderCommand implements OrderMapper{

	@Override
	public OrderCommand orderToOrderCommand(com.springframework.projectshoptoy.domain.Order order) {
		// TODO Auto-generated method stub
		if(order==null) {
			return null;
		}
		OrderCommand orderCommand=new OrderCommand();
		orderCommand.setOrderDate(order.getOrderDate());
		orderCommand.setOrderDetails(order.getOrderDetails());
		orderCommand.setShipCity(order.getShipCity());
		orderCommand.setShippedDate(order.getShippedDate());
		orderCommand.setShipRegion(order.getShipRegion());
		return orderCommand;
	}

	
}
