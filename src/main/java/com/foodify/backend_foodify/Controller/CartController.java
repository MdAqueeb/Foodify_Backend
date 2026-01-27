package com.foodify.backend_foodify.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.foodify.backend_foodify.DTO.AddToCartDTO;
import com.foodify.backend_foodify.DTO.ApiResponse;
import com.foodify.backend_foodify.Entities.Cart;
import com.foodify.backend_foodify.Service.CartService;

@RestController
@RequestMapping("user/{userid}/cart")
public class CartController {

    @Autowired
    private CartService cart_service;

    @PostMapping
    public ResponseEntity<ApiResponse<Cart>> addCart(@PathVariable Long userid, @RequestBody AddToCartDTO addCart){
        Cart crt = cart_service.addToCart(userid, addCart);
        ApiResponse<Cart> response = new ApiResponse<>();
        response.setData(crt);
        response.setSuccess(true);
        response.setMessage("Added To Cart");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
