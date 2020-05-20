package com.springframework.projectshoptoy.api.mapper;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import com.springframework.projectshoptoy.api.commandObject.OrderDetailsCommand;
import com.springframework.projectshoptoy.api.domain.Order;
import com.springframework.projectshoptoy.api.domain.OrderDetails;

@Component
public class OrderDetailsMapper {
	public OrderDetailsCommand orderDetailsToOrderDetailsCommand(OrderDetails orderDetails) {
		OrderDetailsCommand odOrderDetailsCommand=new OrderDetailsCommand();
		odOrderDetailsCommand.setDiscount(orderDetails.getDiscount());
		odOrderDetailsCommand.setQuanity(orderDetails.getQuanity());
		odOrderDetailsCommand.setTotalAmount(odOrderDetailsCommand.getTotalAmount());
		odOrderDetailsCommand.setProductID(orderDetails.getProduct().getProductID());
		return odOrderDetailsCommand;
	}
	public OrderDetails orderDetailsCommandToOrderDetails(OrderDetailsCommand odDetailsCommand) {
		OrderDetails odDetails=new OrderDetails();
		odDetails.setDiscount(odDetailsCommand.getDiscount());
		odDetails.getProduct().setProductID(odDetailsCommand.getProductID());
		odDetails.setQuanity(odDetailsCommand.getQuanity());
		odDetails.setTotalAmount(odDetailsCommand.getTotalAmount());
		return odDetails;
	}

	
}
