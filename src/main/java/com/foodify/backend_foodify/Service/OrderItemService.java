package com.foodify.backend_foodify.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodify.backend_foodify.Entities.Order_item;
import com.foodify.backend_foodify.Repository.OrderItemRepo;

@Service
public class OrderItemService {

    @Autowired private OrderItemRepo orderItemRepo;

    public Order_item addOrderItem(Order_item item){
        return orderItemRepo.save(item);
    }
}

