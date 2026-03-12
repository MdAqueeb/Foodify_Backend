package com.foodify.backend_foodify.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodify.backend_foodify.Entities.Order;
import com.foodify.backend_foodify.Entities.Order_item;
import com.foodify.backend_foodify.Exceptions.ResourceNotFoundException;
import com.foodify.backend_foodify.Repository.OrderItemRepo;
import com.foodify.backend_foodify.Repository.OrderRepo;

@Service
public class OrderItemService {
    
    @Autowired
    private OrderItemRepo orderItemRepo;

    @Autowired
    private OrderRepo orderRepo;
    
    public List<Order_item> getOrderItemsByOrderId(Long orderId) {

        Optional<Order> order = orderRepo.findById(orderId);
        if(!order.isPresent()) {
            throw new ResourceNotFoundException("Order not found with id: " + orderId);
        }
        return orderItemRepo.findByOrderOrder_id(orderId);
    }
}
