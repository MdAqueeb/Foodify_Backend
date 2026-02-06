package com.foodify.backend_foodify.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodify.backend_foodify.Entities.Wishlist;
import com.foodify.backend_foodify.Entities.Wishlist_item;
import com.foodify.backend_foodify.Exceptions.AlreadyExiestException;
import com.foodify.backend_foodify.Repository.Wishlist_item_Repo;


@Service

public class WishlistItemService {

    @Autowired
    private  Wishlist_item_Repo wishlistItemRepo;
     @Autowired
    private  WishlistService wishlistService;

    public Wishlist_item addToWishlist(
            Long userId,
            Long itemId,
            Wishlist_item.Item_type itemType
    ) {

        Wishlist wishlist = wishlistService.getOrCreateWishlist(userId);

        boolean exists =
                wishlistItemRepo.existsWishlistItem(
                        wishlist, itemId, itemType
                );

        if (exists) {
            throw new AlreadyExiestException("Item already exists in wishlist");
        }

        Wishlist_item item = new Wishlist_item();
        item.setItem_id(itemId);
        item.setItem_type(itemType);
        item.setWishlist(wishlist);

        return wishlistItemRepo.save(item);
    }
}
