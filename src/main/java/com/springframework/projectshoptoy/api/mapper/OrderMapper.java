package com.springframework.projectshoptoy.api.mapper;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import com.springframework.projectshoptoy.api.commandObject.OrderCommand;
import com.springframework.projectshoptoy.api.commandObject.OrderDetailsCommand;
import com.springframework.projectshoptoy.api.domain.Order;
import com.springframework.projectshoptoy.api.domain.OrderDetails;
//Chuyển các object thành object Command để đảm bảo dữ liệu không bị vòng lặp trong bi-direction
@Component
public class OrderMapper {
	public OrderCommand orderToOrderCommand(Order order) {
		if(order==null) {
			return null;
		}
		OrderCommand orderCommand=new OrderCommand();
		orderCommand.setOrderDate(order.getOrderDate());

		List<OrderDetailsCommand> ListODDTC=new ArrayList<OrderDetailsCommand>();
		OrderDetailsMapper orderDetailsMapper=new OrderDetailsMapper();
		if(order.getOrderDetails()!=null && order.getOrderDetails().size()>0) {
			order.getOrderDetails().forEach(t->{
				System.out.println(t.getQuanity());
				ListODDTC.add(orderDetailsMapper.orderDetailsToOrderDetailsCommand(t));
			});
		}

		orderCommand.setOrderId(order.getOrderID());
		orderCommand.setOrderDetails(ListODDTC);
		orderCommand.setShipCity(order.getShipCity());
		orderCommand.setShippedDate(order.getShippedDate());
		orderCommand.setShipRegion(order.getShipRegion());
		orderCommand.setCustomerID(order.getCustomer().getCustomerID());
		return orderCommand;
	};
	public Order orderCommandToOrder(OrderCommand orderCommand) {
		Order order=new Order();

		order.getCustomer().setCustomerID(orderCommand.getCustomerID());
		order.setOrderDate(orderCommand.getOrderDate());

		List<OrderDetails> listOrderDetails=new ArrayList<OrderDetails>();
		OrderDetailsMapper orderDetailsMapper=new OrderDetailsMapper();
		if(orderCommand.getOrderDetails()!=null && orderCommand.getOrderDetails().size()>0) {
			orderCommand.getOrderDetails().forEach(t->{
				listOrderDetails.add(orderDetailsMapper.orderDetailsCommandToOrderDetails(t));
			});

		}
		order.setOrderDetails(listOrderDetails);
		order.setOrderID(orderCommand.getOrderId());
		order.setShipCity(orderCommand.getShipCity());
		order.setShippedDate(orderCommand.getShippedDate());
		order.setShipRegion(orderCommand.getShipRegion());
		return order;
	}
}
