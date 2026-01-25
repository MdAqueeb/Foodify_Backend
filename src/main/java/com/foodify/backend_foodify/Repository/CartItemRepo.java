package com.foodify.backend_foodify.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodify.backend_foodify.Entities.Cart_Item;

@Repository
public interface CartItemRepo extends JpaRepository<Cart_Item, Long>{
    
}
