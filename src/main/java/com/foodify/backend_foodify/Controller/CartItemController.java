package com.foodify.backend_foodify.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.foodify.backend_foodify.DTO.AddToCartDTO;
import com.foodify.backend_foodify.DTO.ApiResponse;
import com.foodify.backend_foodify.Entities.Cart;
import com.foodify.backend_foodify.Entities.Cart_Item;
import com.foodify.backend_foodify.Entities.Cart_Restaurent;
import com.foodify.backend_foodify.Service.CartItemService;

@RestController
// @RequestMapping("user/{userid}/cart")
public class CartItemController {
    
    @Autowired
    private CartItemService cartItem_service;

    @PostMapping("user/{userid}")
    public ResponseEntity<ApiResponse<Cart>> addCart(@PathVariable Long userid, @RequestBody AddToCartDTO addCart){
        Cart crt = cartItem_service.addToCart(userid, addCart);
        ApiResponse<Cart> response = new ApiResponse<>();
        response.setData(crt);
        response.setSuccess(true);
        response.setMessage("Added To Cart");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("user/{userid}/cart/{cartId}/{cartItemId}")
    public ResponseEntity<ApiResponse<Cart_Item>> deleteCartItem(@PathVariable Long userid, @PathVariable Long cartId, @PathVariable Long cartItemId){
        Cart_Item crt_itm = cartItem_service.deleteCartItem(userid, cartId, cartItemId);
        ApiResponse<Cart_Item> response = new ApiResponse<>();
        // response.setData(crt_itm);
        response.setSuccess(true);
        response.setMessage("cart item removed successfull");
        return new ResponseEntity<>(response, HttpStatus.OK);
    } 

    @PatchMapping("/items/{cartItemId}")
    public ResponseEntity<Cart_Item> updateQuantity(
            @PathVariable Long cartItemId,
            @RequestParam String action) {

        Cart_Item updatedCart =
                cartItem_service.updateQuantity(cartItemId, action);

        return ResponseEntity.ok(updatedCart);
    }


    // @PatchMapping("cart/items/{cartItemId}/quantity")
    // public ResponseEntity<ApiResponse<Cart_Item>> quantityUpdate()
}
