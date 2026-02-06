package com.foodify.backend_foodify.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodify.backend_foodify.DTO.AddToCartDTO;
import com.foodify.backend_foodify.Entities.*;
import com.foodify.backend_foodify.Exceptions.ResourceNotFoundException;
import com.foodify.backend_foodify.Repository.*;
@Service
public class CartService {

    @Autowired private UserRepo userRepo;
    @Autowired private FoodRepo foodRepo;
    @Autowired private CartRepo cartRepo;
    @Autowired private CartItemRepo cartItemRepo;
    @Autowired private FoodExtraRepo foodExtraRepo;
    @Autowired private CartRestaurentService cartRestService;

    public Cart addToCart(Long userId, AddToCartDTO dto) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Food food = foodRepo.findById(dto.getFoodId())
                .orElseThrow(() -> new ResourceNotFoundException("Food not found"));

        Restaurent restaurent = food.getMenu().getRestaurent();

        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart = cartRepo.save(cart);
        }

        Cart_Restaurent cartRest =
                cartRestService.getOrCreate(cart, restaurent);

        Cart_Item cartItem = cartItemRepo
                .findByCartRestaurentAndFood(cartRest, food)
                .orElseGet(() -> {
                    Cart_Item ci = new Cart_Item();
                    ci.setFood(food);
                    ci.setCartRestaurent(cartRest);
                    ci.setQuantity(0);
                    ci.setPrice(0.0);
                    return ci;
                });

        // quantity update
        cartItem.setQuantity(cartItem.getQuantity() + dto.getQuantity());

        double basePrice = food.getFood_price() * dto.getQuantity();
        double extrasPrice = 0.0;

        for (Long extraId : dto.getAddons()) {
            Food_Extra extra = foodExtraRepo.findById(extraId)
                    .orElseThrow(() -> new ResourceNotFoundException("Addon not found"));

            Cart_items_Extra cie = new Cart_items_Extra();
            cie.setCart_item(cartItem);
            cie.setFood_extra(extra);
            cie.setPrice(extra.getItem_price());

            cartItem.getCart_item_extra().add(cie);
            extrasPrice += extra.getItem_price();
        }

        double itemTotal = basePrice + extrasPrice;

        // ✅ CORRECT incremental pricing
        cartItem.setPrice(cartItem.getPrice() + itemTotal);
        cartItem.setSpecialInstructions(dto.getSpecialInstructions());

        cartItemRepo.save(cartItem);

        // ✅ CORRECT cart totals
        cartRest.setTotalItems(cartRest.getTotalItems() + dto.getQuantity());
        cartRest.setTotalAmount(cartRest.getTotalAmount() + itemTotal);

        return cart;
    }

    public Cart getCartByUser(Long userId) {

    User user = userRepo.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    Cart cart = user.getCart();

    if (cart == null) {
        throw new ResourceNotFoundException("Cart not found for user");
    }

    return cart;
}

    public void clearCart(Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clearCart'");
    }

}
