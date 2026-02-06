package com.foodify.backend_foodify.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodify.backend_foodify.Entities.Cart_Item;
import com.foodify.backend_foodify.Entities.Cart_Restaurent;
import com.foodify.backend_foodify.Entities.Food;

@Repository
public interface CartItemRepo
        extends JpaRepository<Cart_Item, Long> {

    Optional<Cart_Item> findByCartRestaurentAndFood(
            Cart_Restaurent cartRestaurent,
            Food food
    );
}
