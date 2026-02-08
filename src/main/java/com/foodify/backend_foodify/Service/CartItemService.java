package com.foodify.backend_foodify.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodify.backend_foodify.DTO.AddToCartDTO;
import com.foodify.backend_foodify.Entities.Cart;
import com.foodify.backend_foodify.Entities.Cart_Item;
import com.foodify.backend_foodify.Entities.Cart_Restaurent;
import com.foodify.backend_foodify.Entities.Cart_items_Extra;
import com.foodify.backend_foodify.Entities.Food;
import com.foodify.backend_foodify.Entities.Restaurent;
import com.foodify.backend_foodify.Entities.User;
import com.foodify.backend_foodify.Exceptions.ResourceConflictException;
import com.foodify.backend_foodify.Exceptions.ResourceNotFoundException;
import com.foodify.backend_foodify.Repository.CartItemExtraRepo;
import com.foodify.backend_foodify.Repository.CartItemRepo;
import com.foodify.backend_foodify.Repository.CartRepo;
import com.foodify.backend_foodify.Repository.CartRestaurentRepo;
import com.foodify.backend_foodify.Repository.FoodRepo;
import com.foodify.backend_foodify.Repository.RestaurentRepo;
import com.foodify.backend_foodify.Repository.UserRepo;

import jakarta.transaction.Transactional;

@Service
public class CartItemService {
    
    @Autowired
    private CartItemRepo cart_item_repo;

    @Autowired
    private UserRepo user_repo; 

    @Autowired
    private CartRepo cart_repo;

    @Autowired
    private RestaurentRepo rest_repo;

    @Autowired
    private FoodRepo food_repo;

    @Autowired
    private CartRestaurentRepo crt_rst_repo;

    @Autowired
    private CartItemExtraRepo crt_ext_repo;

   @Transactional
    public Cart addToCart(Long userid, AddToCartDTO addCart) {
        User usr = user_repo.findById(userid)
                .orElseThrow(() -> new ResourceNotFoundException("User Not found"));
        Food fd = food_repo.findById(addCart.getFoodId())
                .orElseThrow(() -> new ResourceNotFoundException("Food Not found"));
        Restaurent rst = rest_repo.findById(addCart.getRestaurentId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurent Not found"));

        // Ensure user has a cart
        Cart crt = Optional.ofNullable(usr.getCart())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(usr);
                    usr.setCart(newCart); // bidirectional link
                    return cart_repo.save(newCart);
                });

        // Ensure Cart_Restaurent exists
        Cart_Restaurent crtRst = crt_rst_repo.findByCart_Cart_idAndRestaurent_Restaurant_id(
                crt.getCart_id(), rst.getRestaurant_id()
        ).orElseGet(() -> {
            Cart_Restaurent newCrtRst = new Cart_Restaurent();
            newCrtRst.setCart(crt);
            newCrtRst.setRestaurent(rst);
            newCrtRst.setTotal_amount(0.0);
            newCrtRst.setTotal_items(0);
            return crt_rst_repo.save(newCrtRst);
        });

        // Add or update Cart_Item
        Cart_Item crtItem = cart_item_repo.findByCart_restaurent_Cart_restaurent_idAndFood_Food_id(
                crtRst.getCart_restaurent_id(), fd.getFood_id()
        );

        int quantityToAdd = addCart.getQuantity();
        double priceToAdd = fd.getFood_price() * quantityToAdd;

        if (crtItem == null) {
            crtItem = new Cart_Item();
            crtItem.setCart_restaurent(crtRst);
            crtItem.setFood(fd);
            crtItem.setQuantity(quantityToAdd);
            crtItem.setSpecialInstructions(addCart.getSpecialInstructions());
            crtItem.setPrice(priceToAdd);

            List<Cart_items_Extra> extras = addCart.getAddons().stream()
                    .map(crt_ext_repo::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
            crtItem.setCart_item_extra(extras);

            cart_item_repo.save(crtItem);
            crtRst.setTotal_amount(crtRst.getTotal_amount() + priceToAdd);
            crtRst.setTotal_items(crtRst.getTotal_items() + 1);
        } else {
            crtItem.setQuantity(crtItem.getQuantity() + quantityToAdd);
            crtItem.setPrice(crtItem.getPrice() + priceToAdd);
            cart_item_repo.save(crtItem);

            crtRst.setTotal_amount(crtRst.getTotal_amount() + priceToAdd);
            // total_items remains the same since item already exists
        }

        crt_rst_repo.save(crtRst);
        return crt;
    }


    @Transactional
    public Cart_Item deleteCartItem(Long userid, Long cartId, Long cartItemId) {

        User user = user_repo.findById(userid)
            .orElseThrow(() -> new ResourceNotFoundException("User Not found"));

        Cart_Item item = cart_item_repo.findById(cartItemId)
            .orElseThrow(() -> new ResourceNotFoundException("Cart Item Not found"));

        Cart_Restaurent crt_rst = item.getCart_restaurent();

        if (user.getCart() == null || 
            !user.getCart().getCart_id().equals(cartId)) {
            throw new ResourceConflictException("User cart mismatch");
        }

        if (!crt_rst.getCart().getCart_id().equals(cartId)) {
            throw new ResourceConflictException("Cart restaurant mismatch");
        }

        crt_rst.setTotal_amount(
            crt_rst.getTotal_amount() - item.getPrice()
        );

        crt_rst.setTotal_items(
            crt_rst.getTotal_items() - 1
        );

        cart_item_repo.delete(item);
        if (crt_rst.getTotal_items() == 0) {
            crt_rst_repo.delete(crt_rst);
        } else {
            crt_rst_repo.save(crt_rst);
        }

        return item;
    }

    @Transactional
    public Cart_Item updateQuantity(Long cartItemId, String action) {
        Cart_Item cartItem = cart_item_repo.findById(cartItemId)
            .orElseThrow(() -> new RuntimeException("Cart item not found"));

        Cart_Restaurent cartRestaurent = cartItem.getCart_restaurent();

        if ("INC".equalsIgnoreCase(action)) {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartItem.setPrice(cartItem.getPrice() + cartItem.getFood().getFood_price());
            cartRestaurent.setTotal_amount(cartRestaurent.getTotal_amount() + cartItem.getFood().getFood_price());
            crt_rst_repo.save(cartRestaurent);
            cart_item_repo.save(cartItem);

        } else if ("DEC".equalsIgnoreCase(action)) {
            if (cartItem.getQuantity() > 1) {
                cartItem.setQuantity(cartItem.getQuantity() - 1);
                cartItem.setPrice(cartItem.getPrice() - cartItem.getFood().getFood_price());
                cartRestaurent.setTotal_amount(cartRestaurent.getTotal_amount() - cartItem.getFood().getFood_price());
                cart_item_repo.save(cartItem);
                crt_rst_repo.save(cartRestaurent);
            } else {
                cartRestaurent.getCart_Items().remove(cartItem);
                
                cart_item_repo.delete(cartItem);
            }

        } else {
            throw new IllegalArgumentException("Invalid action. Use INC or DEC");
        }

        return cartItem;       

    }

    
}
