package com.foodify.backend_foodify.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.foodify.backend_foodify.DTO.AddToCartDTO;
import com.foodify.backend_foodify.DTO.ApiResponse;
import com.foodify.backend_foodify.Entities.Cart;
import com.foodify.backend_foodify.Service.CartService;

@RestController
@RequestMapping("/user/{userId}/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    public ResponseEntity<ApiResponse<Cart>> addToCart(
            @PathVariable Long userId,
            @RequestBody AddToCartDTO dto) {

        Cart cart = cartService.addToCart(userId, dto);

        return ResponseEntity.ok(
                new ApiResponse<>(true, cart, "Added to cart")
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Cart>> getCart(
            @PathVariable Long userId) {

        Cart cart = cartService.getCartByUser(userId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, cart, "Cart fetched")
        );
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<String>> clearCart(
            @PathVariable Long userId) {

        cartService.clearCart(userId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Cart cleared", null)
        );
    }
}
