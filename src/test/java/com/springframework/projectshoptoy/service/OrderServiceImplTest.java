package com.springframework.projectshoptoy.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.springframework.projectshoptoy.domain.Order;
import com.springframework.projectshoptoy.domain.OrderDetails;
public class OrderServiceImplTest {
//	OrderServiceImpl orderServiceImpl;
//	@Mock
//	private  OrderRepository orderRepository;
//	@Mock
//	private  CustomerRepository customerRepository;
//	@Mock
//	private  ProductRepository productRepository;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void getListOrderDetailsByOrderID() {
//		Set<Order> orderDetails=orderServiceImpl.getListOrder();//.findOrderById("OD");//.listAllOrderDetailsByIdOrder("OD");
	
	}

}
