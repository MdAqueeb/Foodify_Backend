package com.foodify.backend_foodify.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodify.backend_foodify.Entities.Order;
import com.foodify.backend_foodify.Exceptions.ResourceNotFoundException;
import com.foodify.backend_foodify.Repository.OrderRepo;
@Service
public class OrderService {

    @Autowired private OrderRepo orderRepo;

    public Order createOrder(Order order){
        return orderRepo.save(order);
    }

    public Order getOrder(Long orderId){
        return orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }
}

