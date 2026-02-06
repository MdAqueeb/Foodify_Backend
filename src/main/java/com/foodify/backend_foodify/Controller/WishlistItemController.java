package com.foodify.backend_foodify.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.foodify.backend_foodify.DTO.ApiResponse;
import com.foodify.backend_foodify.Entities.Wishlist_item;
import com.foodify.backend_foodify.Service.WishlistItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistItemController {

    private final WishlistItemService wishlistItemService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Wishlist_item>> addToWishlist(
            @RequestParam Long userId,
            @RequestParam Long itemId,
            @RequestParam Wishlist_item.Item_type itemType
    ) {

        Wishlist_item item =
                wishlistItemService.addToWishlist(userId, itemId, itemType);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, item, "Added to wishlist"));
    }
}
