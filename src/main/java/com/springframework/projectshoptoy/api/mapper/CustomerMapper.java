package com.springframework.projectshoptoy.api.mapper;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import com.springframework.projectshoptoy.api.commandObject.CustomerCommand;
import com.springframework.projectshoptoy.api.commandObject.OrderCommand;
import com.springframework.projectshoptoy.api.domain.Customer;
import com.springframework.projectshoptoy.api.domain.Order;
//Chuyển các object thành object Command để đảm bảo dữ liệu không bị vòng lặp trong bi-direction
@Component
public class CustomerMapper {
	public CustomerCommand customerToCustomerCommand(Customer customer) {
		if(customer==null) {
			return null;
		}
		CustomerCommand customerCommand=new CustomerCommand();
		List<OrderCommand> ListOrderCommand=new ArrayList<OrderCommand>();
		ListOrderCommand.clear();
		OrderMapper odtoOdC=new OrderMapper();
		if(customer.getListOrder()!=null && customer.getListOrder().size()>0) {
			customer.getListOrder().forEach(t->{
				ListOrderCommand.add(odtoOdC.orderToOrderCommand(t));
			});
		}
		
		customerCommand.setAddress(customer.getAddress());
		customerCommand.setCity(customer.getCity());
		customerCommand.setCustomerID(customer.getCustomerID());
		customerCommand.setFirstName(customer.getFirstName());
		customerCommand.setLastName(customer.getLastName());
		customerCommand.setListOrder(ListOrderCommand);
		customerCommand.setUserName(customer.getAccount().getUserName());
		return customerCommand;
	};
	
	public Customer customerCommandToCustomer(CustomerCommand customerCommand) {
		if(customerCommand==null) {
			return null;
		}
		Customer customer=new Customer();
		customer.getAccount().setUserName(customerCommand.getUserName());
		customer.setAddress(customerCommand.getAddress());
		customer.setCity(customerCommand.getCity());
		customer.setCustomerID(customerCommand.getCustomerID());
		customer.setFirstName(customerCommand.getFirstName());
		customer.setLastName(customerCommand.getLastName());
		
		List<Order> listOrder=new ArrayList<Order>();
		listOrder.clear();
		OrderMapper orderMapper=new OrderMapper();
		if(customerCommand.getListOrder()!=null && customerCommand.getListOrder().size()>0) {
			customerCommand.getListOrder().forEach(t->{
				listOrder.add(orderMapper.orderCommandToOrder(t));
			});
		}
		
		customer.setListOrder(listOrder);
		return customer;
	}
}
