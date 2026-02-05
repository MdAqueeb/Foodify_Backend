 package com.foodify.backend_foodify.Service;

// import org.springframework.stereotype.Service;

// @Service
// public class CartItemExtrasService {
    
// }
@Service
public class CartItemService {

    @Autowired private CartItemRepo cartItemRepo;
    @Autowired private CartRepo cartRepo;

    public void updateQuantity(Long userId, Long cartItemId, Integer quantity) {

        Cart_Item item = cartItemRepo.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        if (quantity <= 0) {
            cartItemRepo.delete(item);
            return;
        }

        item.setQuantity(quantity);
        cartItemRepo.save(item);
    }

    public void removeItem(Long userId, Long cartItemId) {
        Cart_Item item = cartItemRepo.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        cartItemRepo.delete(item);
    }
}
