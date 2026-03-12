package com.foodify.backend_foodify.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.foodify.backend_foodify.DTO.ApiResponse;
import com.foodify.backend_foodify.Entities.Order_item;
import com.foodify.backend_foodify.Service.OrderItemService;

@RestController
public class OrderItemController {
    
    @Autowired
    private OrderItemService orderItemService;
    
    @GetMapping("order/{orderId}/items")
    public ResponseEntity<ApiResponse<List<Order_item>>> getOrderItems(@PathVariable Long orderId) {
        List<Order_item> orderItems = orderItemService.getOrderItemsByOrderId(orderId);
        ApiResponse<List<Order_item>> response = new ApiResponse<>();
        response.setData(orderItems);
        response.setSuccess(true);
        response.setMessage("Order items fetched successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
