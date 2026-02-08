package com.foodify.backend_foodify.Service;

import java.util.Optional;

// import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.foodify.backend_foodify.DTO.AddToCartDTO;
import com.foodify.backend_foodify.Entities.Cart;
import com.foodify.backend_foodify.Entities.Cart_Item;
import com.foodify.backend_foodify.Entities.Cart_Restaurent;
import com.foodify.backend_foodify.Entities.Cart_items_Extra;
import com.foodify.backend_foodify.Entities.Food;
import com.foodify.backend_foodify.Entities.Food_Extra;
// import com.foodify.backend_foodify.Entities.Menu;
import com.foodify.backend_foodify.Entities.Restaurent;
import com.foodify.backend_foodify.Entities.User;
import com.foodify.backend_foodify.Exceptions.ResourceNotFoundException;
// import com.foodify.backend_foodify.Repository.CartItemExtraRepo;
import com.foodify.backend_foodify.Repository.CartItemRepo;
import com.foodify.backend_foodify.Repository.CartRepo;
import com.foodify.backend_foodify.Repository.CartRestaurentRepo;
import com.foodify.backend_foodify.Repository.FoodExtraRepo;
// import com.foodify.backend_foodify.Repository.CartRestaurentRepo;
import com.foodify.backend_foodify.Repository.FoodRepo;
// import com.foodify.backend_foodify.Repository.RestaurentRepo;
import com.foodify.backend_foodify.Repository.UserRepo;
@Service
public class CartService {

    @Autowired private UserRepo userRepo;
    @Autowired private FoodRepo foodRepo;
    @Autowired private CartRepo cartRepo;
    @Autowired private CartItemRepo cartItemRepo;
    @Autowired private FoodExtraRepo foodExtraRepo;
    // @Autowired private CartItemExtraRepo cartItemExtraRepo;
    @Autowired private CartRestaurentRepo crt_repo;
    public Page<Cart_Restaurent> getUserCart(Long userid, int page, int size) {
        Optional<User> usr = userRepo.findById(userid);
        if(!usr.isPresent()){
            throw new ResourceNotFoundException("User not found");
        }

        Cart crt = cartRepo.findByUserId(userid);
        if(crt == null){
            crt = new Cart();
            crt.setUser(usr.get());
            crt = cartRepo.save(crt);
        }

        Pageable pge = PageRequest.of(page, size);
        Page<Cart_Restaurent> crt_rs =  crt_repo.getCartRestaurent(crt.getCart_id(), pge);
        return crt_rs;
    }

    // public Cart addToCart(Long userId, AddToCartDTO dto) {

    //     User user = userRepo.findById(userId)
    //             .orElseThrow(() -> 
    //                 new ResourceNotFoundException("User not found"));

    //     Food food = foodRepo.findById(dto.getFoodId())
    //             .orElseThrow(() -> 
    //                 new ResourceNotFoundException("Food not found"));

    //     Restaurent restaurent = food.getMenu().getRestaurent();

    //     Cart cart = user.getCart();
    //     if (cart == null) {
    //         cart = new Cart();
    //         cart.setUser(user);
    //         cart = cartRepo.save(cart);
    //     }

    //     Cart_Restaurent cartRest = cartRestService.getOrCreate(cart, restaurent);

    //     Cart_Item cartItem = cartItemRepo
    //             .findByCart_restaurent_Cart_restaurent_idAndFood_Food_id(
    //                     cartRest.getCart_restaurent_id(),
    //                     food.getFood_id()
    //             )
    //             .orElseGet(() -> {
    //                 Cart_Item ci = new Cart_Item();
    //                 ci.setFood(food);
    //                 ci.setCart_restaurent(cartRest);
    //                 ci.setQuantity(0);
    //                 ci.setPrice(0.0);
    //                 return ci;
    //             });

    //     cartItem.setQuantity(cartItem.getQuantity() + dto.getQuantity());
    //     double basePrice = food.getFood_price() * dto.getQuantity();

    //     double extrasPrice = 0.0;

    //     for (Long foodExtraId : dto.getAddons()) {

    //         Food_Extra foodExtra = foodExtraRepo.findById(foodExtraId)
    //             .orElseThrow(() -> new ResourceNotFoundException("Addon not found"));

    //         Cart_items_Extra cie = new Cart_items_Extra();
    //         cie.setCart_item(cartItem);
    //         cie.setFood_extra(foodExtra);
    //         cie.setPrice(foodExtra.getItem_price());
    //         // cie.setSpecialInstructions(dto.getSpecialInstructions());

    //         cartItem.getCart_item_extra().add(cie);
    //         extrasPrice += foodExtra.getItem_price();
    //     }

    //     cartItem.setSpecialInstructions(dto.getSpecialInstructions());

    //     cartItem.setPrice(basePrice + extrasPrice);
    //     cartItemRepo.save(cartItem);
    //     cartRest.setTotal_items(cartRest.getTotal_items() + dto.getQuantity());
    //     cartRest.setTotal_amount(cartRest.getTotal_amount()+ cartItem.getPrice());

    //     return cart;
    // }
}

