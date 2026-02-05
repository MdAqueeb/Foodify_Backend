package com.foodify.backend_foodify.Controller;

// import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired private OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<Order>> createOrder(@RequestBody Order order){
        Order savedOrder = orderService.createOrder(order);
        ApiResponse<Order> response = new ApiResponse<>(savedOrder, true, "Order Created");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<Order>> getOrder(@PathVariable Long orderId){
        Order order = orderService.getOrder(orderId);
        ApiResponse<Order> response = new ApiResponse<>(order, true, "Order Fetched");
        return ResponseEntity.ok(response);
    }
}

