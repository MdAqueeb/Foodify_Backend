package com.foodify.backend_foodify.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodify.backend_foodify.Entities.Cart;
import com.foodify.backend_foodify.Entities.Cart_Restaurent;
import com.foodify.backend_foodify.Entities.Restaurent;
import com.foodify.backend_foodify.Repository.CartRestaurentRepo;

@Service
public class CartRestaurentService {

    @Autowired
    private CartRestaurentRepo cartRestRepo;

    public Cart_Restaurent getOrCreate(
            Cart cart,
            Restaurent restaurent
    ) {
        return cartRestRepo
                .findByCart_Cart_idAndRestaurent_Restaurant_id(
                        cart.getCart_id(),
                        restaurent.getRestaurant_id()
                )
                .orElseGet(() -> {
                    Cart_Restaurent cr = new Cart_Restaurent();
                    cr.setCart(cart);
                    cr.setRestaurent(restaurent);
                    cr.setTotal_amount(0.0);
                    cr.setTotal_items(0);
                    return cartRestRepo.save(cr);
                });
    }
}

