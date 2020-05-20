package com.springframework.projectshoptoy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.springframework.projectshoptoy.api.domain.Account;
import com.springframework.projectshoptoy.api.domain.Category;
import com.springframework.projectshoptoy.api.domain.Customer;
import com.springframework.projectshoptoy.api.domain.Order;
import com.springframework.projectshoptoy.api.domain.OrderDetails;
import com.springframework.projectshoptoy.api.domain.Product;
import com.springframework.projectshoptoy.api.domain.Supplier;
import com.springframework.projectshoptoy.dao.MyEntityManager;
import com.springframework.projectshoptoy.exception.ConflixIdException;
import com.springframework.projectshoptoy.exception.NotFoundException;
import com.springframework.projectshoptoy.service.AccountService;
import com.springframework.projectshoptoy.service.AccountServiceImpl;
import com.springframework.projectshoptoy.service.CategoryService;
import com.springframework.projectshoptoy.service.CategoryServiceImpl;
import com.springframework.projectshoptoy.service.CustomerService;
import com.springframework.projectshoptoy.service.CustomerServiceImpl;
import com.springframework.projectshoptoy.service.OrderService;
import com.springframework.projectshoptoy.service.OrderServiceImpl;
import com.springframework.projectshoptoy.service.ProductService;
import com.springframework.projectshoptoy.service.ProductServiceImpl;
import com.springframework.projectshoptoy.service.SupplierService;
import com.springframework.projectshoptoy.service.SupplierServiceImpl;

public class TestConsole {
	public static void main(String[] args) {
		CustomerServiceImpl customerServiceImpl=new CustomerServiceImpl();
		customerServiceImpl.getCustomers().forEach(t->{
			System.out.println(t.getAddress());
		});
//		MyEntityManager myEntityManager=new MyEntityManager();
//						init();
//		//		MyEntityManager myEntityManager=new MyEntityManager();
//		SupplierService supplierService=new SupplierServiceImpl();
//		ProductService productService=new ProductServiceImpl();
//		CategoryService categoryService=new CategoryServiceImpl();
//		OrderService orderService=new OrderServiceImpl();
//		CustomerService customerService=new CustomerServiceImpl();
//		AccountService accountService=new AccountServiceImpl();
////		
//		System.out.println(accountService.deleteAccount("admin"));
	}


	public static void init() {
		SupplierService supplierService=new SupplierServiceImpl();
		ProductService productService=new ProductServiceImpl();
		CategoryService categoryService=new CategoryServiceImpl();
		OrderService orderService=new OrderServiceImpl();
		CustomerService customerService=new CustomerServiceImpl();
		AccountService accountService=new AccountServiceImpl();

		Account account=new Account("admin", "Khanhhoa123#","quangnuyen@gmail.com",true );
		Customer customer=new Customer("33/16 duong huynh van", "ho chi minh", "quan", "nguyen");
		Category category=new Category("caasdasd", "fuck fuck", "picture.jpg");
		Supplier supplier=new Supplier("0708821227", "abc abc", "33/16 asdasd");
		Product product=new Product(true, "fuck fuyck", "kim quyen", 50, 50, category, supplier);
		Product product2=new Product(true, "coms uon", "noise", 50, 50, category, supplier);
		OrderDetails orderDetails=new OrderDetails(20, 20, 10, product);
		OrderDetails orderDetails2=new OrderDetails(20, 20, 10, product2);
		Order order=new Order(LocalDate.of(2020, 12, 12), "hoi chiu minh", "ha noi", LocalDate.of(2020, 12, 12)
				, customer, Arrays.asList(orderDetails,orderDetails2));
		accountService.createNewAccount(account);
		customerService.createNewCustomer(account.getUserName(), customer);
		categoryService.createNewCategory(category);
		supplierService.createNewSupplier(supplier);
		productService.createNewProduct(product);
		productService.createNewProduct(product2);
		orderService.createNewOrder(order);
	}
}
