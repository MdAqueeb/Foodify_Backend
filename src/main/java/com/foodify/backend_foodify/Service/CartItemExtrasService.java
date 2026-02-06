 package com.foodify.backend_foodify.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodify.backend_foodify.Entities.Cart_Item;
import com.foodify.backend_foodify.Exceptions.ResourceNotFoundException;
import com.foodify.backend_foodify.Repository.CartItemRepo;
import com.foodify.backend_foodify.Repository.CartRepo;

// import org.springframework.stereotype.Service;

// @Service
// public class CartItemExtrasService {
    
// }
@Service
public class CartItemExtrasService {

    @Autowired private CartItemRepo cartItemRepo;
    @Autowired private CartRepo cartRepo;

    public void updateQuantity(Long userId, Long cartItemId, Integer quantity) {

        Cart_Item item = cartItemRepo.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        if (quantity <= 0) {
            cartItemRepo.delete(item);
            return;
        }

        item.setQuantity(quantity);
        cartItemRepo.save(item);
    }

    public void removeItem(Long userId, Long cartItemId) {
        Cart_Item item = cartItemRepo.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        cartItemRepo.delete(item);
    }
}
