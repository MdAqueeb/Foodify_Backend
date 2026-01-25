package com.foodify.backend_foodify.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodify.backend_foodify.Entities.Cart_items_Extra;

@Repository
public interface CartItemExtraRepo extends JpaRepository<Cart_items_Extra, Long> {
    
}
