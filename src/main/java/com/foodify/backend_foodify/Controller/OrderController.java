package com.foodify.backend_foodify.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import com.foodify.backend_foodify.DTO.DashboardDTO;

import com.foodify.backend_foodify.DTO.ApiResponse;
import com.foodify.backend_foodify.DTO.EarningDTO;
import com.foodify.backend_foodify.DTO.OrderReqDTO;
import com.foodify.backend_foodify.DTO.OrderStatusDTO;
import com.foodify.backend_foodify.Entities.Order;
import com.foodify.backend_foodify.Service.OrderService;

@RestController
public class OrderController {
    
    @Autowired private OrderService ord_service;

    @PostMapping("/order")
    public ResponseEntity<ApiResponse<Order>> createOrder(@RequestBody OrderReqDTO order, @RequestParam Long cartRestaurent){
          System.out.println("UserId -> " + order.getUserId());
    System.out.println("RestaurantId -> " + order.getRestaurantId());
    System.out.println("order -> " + order);
        
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

    @GetMapping("user/{userId}/orders/active")
    public ResponseEntity<ApiResponse<List<Order>>> getUserActiveOrders(@PathVariable Long userId) {
        List<Order> orders = ord_service.getUserActiveOrders(userId);
        ApiResponse<List<Order>> response = new ApiResponse<>();
        response.setData(orders);
        response.setSuccess(true);
        response.setMessage("Active orders fetched successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("user/{userId}/orders/history")
    public ResponseEntity<ApiResponse<List<Order>>> getUserHistoryOrders(@PathVariable Long userId) {
        List<Order> orders = ord_service.getUserHistoryOrders(userId);
        ApiResponse<List<Order>> response = new ApiResponse<>();
        response.setData(orders);
        response.setSuccess(true);
        response.setMessage("Order history fetched successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // @GetMapping("user/{userId}/orders/active")
     @GetMapping("restaurent/{restaurentId}/orders/active")
    public ResponseEntity<ApiResponse<List<Order>>> getUserActiveOrdersByAdmin(@PathVariable Long restaurentId) {
        List<Order> orders = ord_service.getUserActiveOrdersByAdmin(restaurentId);
        ApiResponse<List<Order>> response = new ApiResponse<>();
        response.setData(orders);
        response.setSuccess(true);
        response.setMessage("Active orders fetched successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // @GetMapping("user/{userId}/orders/history")
     @GetMapping("restaurent/{restaurentId}/orders/history")
    public ResponseEntity<ApiResponse<List<Order>>> getUserHistoryOrdersByAdmin(@PathVariable Long restaurentId) {
        List<Order> orders = ord_service.getUserHistoryOrdersByAdmin(restaurentId);
        ApiResponse<List<Order>> response = new ApiResponse<>();
        response.setData(orders);
        response.setSuccess(true);
        response.setMessage("Order history fetched successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("{userId}/orders/earnings")
    public ResponseEntity<ApiResponse<EarningDTO>> getEarningDetails(@PathVariable Long userId) {
         EarningDTO earning = ord_service.getEarningData(userId);
        // List<Order> orders = ord_service.getEarningData(restaurentId);
        ApiResponse<EarningDTO> response = new ApiResponse<>();
        response.setData(earning);
        response.setSuccess(true);
        response.setMessage("Earning Data fetched successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // today's orders, active orders, restaurent Count, list of sales
    @GetMapping("{userId}/orders/dashboard")
    public ResponseEntity<ApiResponse<DashboardDTO>> getDashboardDetails(@PathVariable Long userId) {
         DashboardDTO dashboard = ord_service.getDashboardData(userId);
        // List<Order> orders = ord_service.getEarningData(restaurentId);
        ApiResponse<DashboardDTO> response = new ApiResponse<>();
        response.setData(dashboard);
        response.setSuccess(true);
        response.setMessage("Dashboard Data fetched successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
