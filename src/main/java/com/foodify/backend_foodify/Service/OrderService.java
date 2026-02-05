package com.foodify.backend_foodify.Service;

import org.springframework.stereotype.Service;
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

