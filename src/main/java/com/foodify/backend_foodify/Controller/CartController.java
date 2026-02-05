package com.foodify.backend_foodify.Controller;
@RestController
@RequestMapping("user/{userId}/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // ADD TO CART (already working)
    @PostMapping
    public ResponseEntity<ApiResponse<Cart>> addToCart(
            @PathVariable Long userId,
            @RequestBody AddToCartDTO dto) {

        Cart cart = cartService.addToCart(userId, dto);

        return ResponseEntity.ok(
            new ApiResponse<>(true, "Added To Cart", cart)
        );
    }

    // GET CART (load cart page)
    @GetMapping
    public ResponseEntity<ApiResponse<Cart>> getCart(
            @PathVariable Long userId) {

        Cart cart = cartService.getCartByUser(userId);

        return ResponseEntity.ok(
            new ApiResponse<>(true, "Cart fetched", cart)
        );
    }

    // CLEAR CART
    @DeleteMapping
    public ResponseEntity<ApiResponse<String>> clearCart(
            @PathVariable Long userId) {

        cartService.clearCart(userId);

        return ResponseEntity.ok(
            new ApiResponse<>(true, "Cart cleared", null)
        );
    }
}
