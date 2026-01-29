package com.foodify.backend_foodify.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodify.backend_foodify.Entities.Order_item;

@Repository
public interface OrderItemRepo extends JpaRepository<Order_item, Long> {
    
}
