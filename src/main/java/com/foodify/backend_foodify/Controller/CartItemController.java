package com.foodify.backend_foodify.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.foodify.backend_foodify.DTO.ApiResponse;
import com.foodify.backend_foodify.Service.CartItemService;

@RestController
@RequestMapping("/user/{userId}/cart/item")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @PutMapping("/{cartItemId}")
    public ResponseEntity<ApiResponse<String>> updateQuantity(
            @PathVariable Long userId,
            @PathVariable Long cartItemId,
            @RequestParam Integer quantity) {

        cartItemService.updateQuantity(userId, cartItemId, quantity);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Quantity updated", null)
        );
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<ApiResponse<String>> removeItem(
            @PathVariable Long userId,
            @PathVariable Long cartItemId) {

        cartItemService.removeItem(userId, cartItemId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Item removed", null)
        );
    }
}
