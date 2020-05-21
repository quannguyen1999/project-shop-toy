package com.springframework.projectshoptoy.bootstrap;

import com.springframework.projectshoptoy.api.domain.*;
import com.springframework.projectshoptoy.service.AccountService;
import com.springframework.projectshoptoy.service.CategoryService;
import com.springframework.projectshoptoy.service.CustomerService;
import com.springframework.projectshoptoy.service.OrderService;
import com.springframework.projectshoptoy.service.ProductService;
import com.springframework.projectshoptoy.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.Arrays;
//@Component là  bean,được tiêm vào spring container để khởi tạo
//ApplicationListener chạy trước khi spring boot chạy
@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {
	//@Autowired Tìm kiếm bean service để tiêm vào
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private AccountService accountService;
	
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
    	//load dữ liệu
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
