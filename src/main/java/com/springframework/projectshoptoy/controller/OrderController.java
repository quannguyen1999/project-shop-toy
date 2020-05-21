package com.springframework.projectshoptoy.controller;

import com.springframework.projectshoptoy.api.commandObject.OrderCommand;
import com.springframework.projectshoptoy.api.domain.ErrorException;
import com.springframework.projectshoptoy.api.domain.Order;
import com.springframework.projectshoptoy.api.domain.OrderDetails;
import com.springframework.projectshoptoy.api.domain.Supplier;
import com.springframework.projectshoptoy.service.OrderService;
import com.springframework.projectshoptoy.service.SupplierService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.Set;
//@Api:Mô tả api trong swagger-ui
//@Slf4j để dùng logger.info,...
//@RequiredArgsConstructor:để tạo constructor có tham số
//@RequestMapping:tên trang để truy cập
//@RestController :đăng ký Bean controler
@Api(description = "order")
@Slf4j
@RequiredArgsConstructor
@RequestMapping(OrderController.BASE_URL)
@RestController
public class OrderController {
    public final static String  BASE_URL="/api/orders";
    private final OrderService orderService;
    
    //@ApiOperaion:đổi tên trong swagger-ui
    //@GetMapping:Type:Get
    //@Responstatus trả về kiểu status
    //lấy danh sách Order
    @ApiOperation(value = "lấy danh sách Order")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Set<OrderCommand> orders(){
        return orderService.getListOrder();
    }

//    //lấy danh sách chi tiết của order
//    @ApiOperation(value = "lấy danh sách chi tiết của order")
//    @GetMapping("/{id}/orderDetails")
//    @ResponseStatus(HttpStatus.OK)
//    public Set<OrderDetails> orderDetails(@PathVariable String id){
//        return orderService.getListOrderDetails(id);
//    }

    //thêm mới Order
    @ApiOperation(value = "thêm mới Order")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderCommand createNewOrder(@Valid @RequestBody Order order){
        return orderService.createNewOrder(order);
    }

//    //thêm mới orderDetails
//    @ApiOperation(value = "thêm mới orderDetails ")
//    @PostMapping("/{id}/orderDetails")
//    @ResponseStatus(HttpStatus.CREATED)
//    public OrderDetails createNewOrderDetails(@PathVariable String id,@Valid @RequestBody OrderDetails orderDetails){
//        return orderService.createNewOrderDetail(id,orderDetails);
//    }

    //tìm kiếm order bằng id
    @ApiOperation(value = "tìm kiếm order bằng id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderCommand findOrderByID(@PathVariable String id){
        return orderService.findOrderById(id);
    }
    
    //tìm kiếm danh sách order của khách hàng 
    @ApiOperation(value = "tìm kiếm danh sách order của khách hàng ")
    @GetMapping("/customer/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Set<OrderCommand> getListOrderByCustomerID(@PathVariable String id){
    	return orderService.getListOrderByCustomerID(id);
    }

//    //tìm kiếm 1 orderDetails bằng idOrderDetails
//    @ApiOperation(value = "tìm kiếm 1 orderDetails bằng idOrderDetails")
//    @GetMapping("/{id}/orderDetails/{orderDetailsID}")
//    @ResponseStatus(HttpStatus.OK)
//    public OrderDetails findOrderDetailByID(@PathVariable String id,@PathVariable String orderDetailsID){
//        return orderService.findOrderDetailById(id,orderDetailsID);
//    }

    //xóa Order bằng id
    @ApiOperation(value = "xóa Order bằng id")
    @DeleteMapping("/{id}")
    public ResponseEntity<ErrorException> deleteOrder(@PathVariable String id){
        log.debug("deleting id:"+id);
        orderService.deleteOrder(id);
        ErrorException errorException=new ErrorException();
        errorException.setStatus(HttpStatus.OK.toString());
        errorException.setError("delete success");
        return new ResponseEntity<ErrorException>(errorException,HttpStatus.OK);
    }

//    //xóa orderDetails bằng id
//    @ApiOperation(value = "xóa orderDetails bằng id")
//    @DeleteMapping("/{id}/orderDetails/{orderDetailsID}")
//    public ResponseEntity<ErrorException> deleteOrderDetails(@PathVariable String id,@PathVariable String orderDetailsID){
//        log.debug("deleting id:"+id);
//        orderService.deleteOrderDetails(id,orderDetailsID);
//        ErrorException errorException=new ErrorException();
//        errorException.setStatus(HttpStatus.OK.toString());
//        errorException.setError("delete success");
//        return new ResponseEntity<ErrorException>(errorException,HttpStatus.OK);
//    }

    //cập nhập Order
    @ApiOperation(value = "cập nhập Order")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderCommand updateOrder(@PathVariable String id,@RequestBody Order order){
        return orderService.updateOrder(id,order);
    }

//    //cập nhập orderDetails
//    @ApiOperation(value = "cập nhập orderDetails")
//    @PutMapping("/{id}/orderDetails/{orderDetailsID}")
//    @ResponseStatus(HttpStatus.OK)
//    public OrderDetails updateOrderDetails(@PathVariable String id,@PathVariable String orderDetailsID,@RequestBody OrderDetails orderDetails){
//        return orderService.updateOrderDetails(id,orderDetailsID,orderDetails);
//    }

}
