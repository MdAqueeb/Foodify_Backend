package com.foodify.backend_foodify.Service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodify.backend_foodify.Entities.User;
import com.foodify.backend_foodify.Entities.Wishlist;
import com.foodify.backend_foodify.Exceptions.ResourceNotFoundException;
import com.foodify.backend_foodify.Repository.UserRepo;
import com.foodify.backend_foodify.Repository.WishlistRepo;



@Service

public class WishlistService {

    @Autowired
    private  WishlistRepo wishlistRepo;
    @Autowired
    private  UserRepo userRepo;

    public Wishlist getOrCreateWishlist(Long userId) {

        return wishlistRepo.findByUser_User_id(userId)
                .orElseGet(() -> {
                    User user = userRepo.findById(userId)
                            .orElseThrow(() ->
                                    new ResourceNotFoundException("User not found"));

                    Wishlist wishlist = new Wishlist();
                    wishlist.setUser(user);
                    wishlist.setWishlist_items(new ArrayList<>());

                    return wishlistRepo.save(wishlist);
                });
    }
}
