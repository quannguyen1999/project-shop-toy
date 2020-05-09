package com.springframework.projectshoptoy.mapper;

import com.springframework.projectshoptoy.commandObject.OrderCommand;
import com.springframework.projectshoptoy.domain.Order;

public interface OrderMapper {
	OrderCommand orderToOrderCommand(Order order);
}
