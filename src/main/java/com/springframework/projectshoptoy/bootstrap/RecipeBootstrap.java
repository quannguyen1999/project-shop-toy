package com.springframework.projectshoptoy.bootstrap;

import com.springframework.projectshoptoy.domain.*;
import com.springframework.projectshoptoy.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        loadData();
//        loadCategory();
    }

    private void loadCategory() {
        Account account=new Account();
        account.setUserName("admin");
        account.setPassword(passwordEncoder.encode("fuck"));
        account.setAccType(true);

        Account account1=new Account();
        account1.setUserName("employee");
        account1.setPassword(passwordEncoder.encode("123"));
        if(accountRepository.findById(account.getUserName()).isPresent()==false){
            accountRepository.save(account);
        }

        if(accountRepository.findById(account1.getUserName()).isPresent()==false){
            accountRepository.save(account1);
        }

        Customer customer=new Customer();
        customer.setCustomerID("KH"+ObjectId.get().toString());
        customer.setAccount(account1);
        customer.setAddress("33/16");
        customer.setCity("usa");
        customer.setEmail("employee@gmail.com");
        customer.setFirstName("kim");
        customer.setLastName("quyen");

        if(customerRepository.findCustomerByUserName(customer.getAccount().getUserName())==null){
            customerRepository.save(customer);
        }

        Category category=new Category();
        category.setCategoryID("CT"+ObjectId.get().toString());
        category.setCategoryName("abc");
        category.setPicture("1.png");
        category.setDescription("mổ tả");

        log.debug("abc:"+category.toString());

        categoryRepository.save(category);

        Supplier supplier=new Supplier();
        supplier.setSupplierID("SL"+ObjectId.get().toString());
        supplier.setCompanyName("abc");
        supplier.setAddress("33/16");
        supplier.setPhone("113");
        supplierRepository.save(supplier);

        
        Product product=new Product();
        product.setProductID("P"+ObjectId.get().toString());
        product.setQuantityInStock(10);
        product.setProductName("cơm sườn");
        product.setMoTa("hết hàng");
        product.setDiscontinued(true);
        product.setUnitPrice(10);
        product.setCategory(category);
        product.setSupplier(supplier);
        productRepository.save(product);

        Product product2=new Product();
        product2.setProductID("P2"+ObjectId.get().toString());
        product2.setQuantityInStock(10);
        product2.setProductName("bún sườn");
        product2.setMoTa("còn hàng");
        product2.setDiscontinued(true);
        product2.setUnitPrice(10);
        product2.setCategory(category);
        product2.setSupplier(supplier);
        productRepository.save(product2);

        Order order=new Order();
        order.setOrderID("OD"+ObjectId.get().toString());
        order.setShipCity("hồ chí minh");
        order.setCustomer(customer);
        order.setShipRegion("japan");
        order.setShippedDate(LocalDate.now());
        order.setOrderDate(LocalDate.now());
        orderRepository.save(order);

        OrderDetails orderDetails=new OrderDetails();
        orderDetails.setOrderDetailID("ODDT"+ObjectId.get().toString());
        orderDetails.setDiscount(10);
        orderDetails.setProduct(product);
        orderDetails.setQuanity(10);
        orderDetails.setTotalAmount(100);
        orderDetails.setOrder(order);
        orderDetailRepository.save(orderDetails);

        OrderDetails orderDetails1=new OrderDetails();
        orderDetails1.setOrderDetailID("ODDT"+ObjectId.get().toString());
        orderDetails1.setDiscount(20);
        orderDetails1.setProduct(product2);
        orderDetails1.setQuanity(10);
        orderDetails1.setTotalAmount(100);
        orderDetails1.setOrder(order);
        orderDetailRepository.save(orderDetails1);
    }

    private void loadData() {


    }
}
