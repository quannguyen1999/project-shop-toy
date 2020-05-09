package com.springframework.projectshoptoy.bootstrap;

import com.springframework.projectshoptoy.dao.MyEntityManager;
import com.springframework.projectshoptoy.domain.*;
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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.Arrays;

@Slf4j
@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired
	private SupplierService supplierService;//=new SupplierServiceImpl();
	@Autowired
	private ProductService productService;//=new ProductServiceImpl();
	@Autowired
	private CategoryService categoryService;//=new CategoryServiceImpl();
	@Autowired
	private OrderService orderService;//=new OrderServiceImpl();
	@Autowired
	private CustomerService customerService;//=new CustomerServiceImpl();
	@Autowired
	private AccountService accountService;//=new AccountServiceImpl();
	
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        loadCategory();
    }

    private void loadCategory() {
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
