package com.foodify.backend_foodify.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodify.backend_foodify.Entities.User;
import com.foodify.backend_foodify.Entities.Wishlist;
import com.foodify.backend_foodify.Entities.Wishlist_item;
import com.foodify.backend_foodify.Entities.Wishlist_item.Item_type;
import com.foodify.backend_foodify.Exceptions.ResourceConflictException;
import com.foodify.backend_foodify.Exceptions.ResourceNotFoundException;
import com.foodify.backend_foodify.Repository.UserRepo;
import com.foodify.backend_foodify.Repository.WishlistRepo;
import com.foodify.backend_foodify.Repository.Wishlist_item_Repo;

@Service
public class WishlistService {
    
    @Autowired
    private WishlistRepo wistlistRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private Wishlist_item_Repo itemRepo;

    public Wishlist_item addItem(Wishlist_item itm, Long userid) {
       Optional<User> usr = userRepo.findById(userid);
        if(!usr.isPresent()){
            throw new ResourceNotFoundException("User not found");
        }
        User user = usr.get();
        Wishlist favorates = user.getWishlists();
        if(favorates == null) {
            Wishlist wshlst = new Wishlist();
            wshlst.setUser(user);
            favorates = wistlistRepo.save(wshlst);
        }
        Wishlist_item item = itm;
        item.setWishlist(favorates);
        return itemRepo.save(item);
    }

    public List<Wishlist_item> getItems(Long userid) {
        Optional<User> usr = userRepo.findById(userid);
        if(!usr.isPresent()){
            throw new ResourceNotFoundException("User not found");
        }
        if (usr.get().getWishlists() == null){
            return new ArrayList<>();
        }
        return usr.get().getWishlists().getWishlist_items();
    }

    public List<Wishlist_item> getItems(Long userid, String type) {
        Optional<User> usr = userRepo.findById(userid);
        if(!usr.isPresent()){
            throw new ResourceNotFoundException("User not found");
        }
        User user = usr.get();

        Wishlist favorates = user.getWishlists();
        

        if(favorates == null) {
            System.out.println("null it is wishlist object");
            return new ArrayList<>();
        }
        try {
            Item_type itemType = Item_type.valueOf(type.toLowerCase());
            System.out.println(type+" it is type &"+ itemType);
            List<Wishlist_item> res = itemRepo.findByItemType(itemType.name());
            System.out.println(" ==========> below is output \n"+res);
            return res;
        } catch (IllegalArgumentException e) {
            throw new ResourceNotFoundException(type+" Not Found");
        }
        
    }

    public Wishlist_item removeItem(Long userid, Long wishlistItemId) {
        Optional<User> usr = userRepo.findById(userid);
        if(!usr.isPresent()){
            throw new ResourceNotFoundException("User not found");
        }
        User user = usr.get();
        Wishlist favorates = user.getWishlists();
        if(favorates == null) {
            throw new ResourceConflictException("user have Empty Wishlist");
        }

        Optional<Wishlist_item> itm = itemRepo.findById(wishlistItemId);
        if(!itm.isPresent()){
            throw new ResourceNotFoundException("item not found");
        }
        else if(!itm.get().getWishlist().getWishlist_id().equals(favorates.getWishlist_id())){
            throw new ResourceConflictException("The given wishlist Not match to this user");
        }

        itemRepo.deleteById(wishlistItemId);
        return itm.get();
    }
}
