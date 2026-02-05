package com.foodify.backend_foodify.Controller;
@RestController
@RequestMapping("user/{userId}/cart/item")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    // UPDATE QUANTITY (+ / -)
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

    // REMOVE ITEM
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
