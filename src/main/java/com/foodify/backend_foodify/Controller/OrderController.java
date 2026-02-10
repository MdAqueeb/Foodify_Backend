package com.foodify.backend_foodify.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import com.foodify.backend_foodify.DTO.ApiResponse;
import com.foodify.backend_foodify.DTO.OrderStatusDTO;
import com.foodify.backend_foodify.Entities.Order;
import com.foodify.backend_foodify.Service.OrderService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class OrderController {
    
    @Autowired private OrderService ord_service;

    @PostMapping("order")
    public ResponseEntity<ApiResponse<Order>> createOrder(@RequestParam Long cartRestaurent, @RequestBody Order order){
        Order ordr = ord_service.createOrder(cartRestaurent, order);
        ApiResponse<Order> response = new ApiResponse<>();
        response.setData(ordr);
        response.setSuccess(true);
        response.setMessage("Order created successfull");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("order/{orderId}")
    public ResponseEntity<ApiResponse<Order>> updateStatus(@PathVariable Long orderId, @RequestBody OrderStatusDTO updateStatus){
        Order ordr = ord_service.changeStatus(orderId, updateStatus);
        ApiResponse<Order> response = new ApiResponse<>();
        response.setData(ordr);
        response.setSuccess(true);
        response.setMessage("Order Status updated successfull");
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    // @GetMapping("")
    // public ResponseEntity<ApiResponse<Page<Order>>> getAllActiveOrders(){
        
    // }
}
