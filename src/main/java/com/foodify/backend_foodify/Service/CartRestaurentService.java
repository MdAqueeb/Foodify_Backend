package com.foodify.backend_foodify.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.foodify.backend_foodify.Entities.Cart;
import com.foodify.backend_foodify.Entities.Cart_Item;
import com.foodify.backend_foodify.Entities.Cart_Restaurent;
import com.foodify.backend_foodify.Entities.Restaurent;
import com.foodify.backend_foodify.Entities.User;
import com.foodify.backend_foodify.Exceptions.ResourceNotFoundException;
import com.foodify.backend_foodify.Repository.CartItemRepo;
import com.foodify.backend_foodify.Repository.CartRestaurentRepo;
import com.foodify.backend_foodify.Repository.UserRepo;

@Service
public class CartRestaurentService {

    @Autowired
    private CartRestaurentRepo cartRestRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CartItemRepo crt_itm_repo;

    public Page<Cart_Item> getAllItems(Long userid, Long cartRestaurentId, int page, int size) {
        
        Optional<User> usr = userRepo.findById(userid);
        if(!usr.isPresent()){
            throw new ResourceNotFoundException("User not found");
        }

        Optional<Cart_Restaurent> crt_rst = cartRestRepo.findById(cartRestaurentId);
        if(!crt_rst.isPresent()){
            throw new ResourceNotFoundException("cart Restaurent id not found");
        }

        Pageable pge = PageRequest.of(page, size);
        Page<Cart_Item> crt_itms =  crt_itm_repo.getCartRestaurentItems(cartRestaurentId, pge);
        return crt_itms;
    }

    // public Cart_Restaurent getOrCreate(
    //         Cart cart,
    //         Restaurent restaurent
    // ) {
    //     return cartRestRepo
    //             .findByCart_Cart_idAndRestaurent_Restaurant_id(
    //                     cart.getCart_id(),
    //                     restaurent.getRestaurant_id()
    //             )
    //             .orElseGet(() -> {
    //                 Cart_Restaurent cr = new Cart_Restaurent();
    //                 cr.setCart(cart);
    //                 cr.setRestaurent(restaurent);
    //                 cr.setTotal_amount(0.0);
    //                 cr.setTotal_items(0);
    //                 return cartRestRepo.save(cr);
    //             });
    // }

    
}

