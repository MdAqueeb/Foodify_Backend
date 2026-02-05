package com.foodify.backend_foodify.Service;

import org.springframework.stereotype.Service;

@Service
public class OrderItemService {

    @Autowired private OrderItemRepo orderItemRepo;

    public Order_item addOrderItem(Order_item item){
        return orderItemRepo.save(item);
    }
}

