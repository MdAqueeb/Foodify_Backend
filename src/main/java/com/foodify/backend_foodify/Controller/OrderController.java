package com.foodify.backend_foodify.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.foodify.backend_foodify.DTO.ApiResponse;
import com.foodify.backend_foodify.Entities.Order;
import com.foodify.backend_foodify.Service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<Order>> createOrder(
            @RequestBody Order order) {

        Order saved = orderService.createOrder(order);

        return ResponseEntity.ok(
                new ApiResponse<>(true, saved, "Order created")
        );
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<Order>> getOrder(
            @PathVariable Long orderId) {

        Order order = orderService.getOrder(orderId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, order, "Order fetched")
        );
    }
}
