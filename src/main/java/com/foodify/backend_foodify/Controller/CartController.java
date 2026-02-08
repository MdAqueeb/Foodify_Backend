package com.foodify.backend_foodify.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.foodify.backend_foodify.DTO.AddToCartDTO;
import com.foodify.backend_foodify.DTO.ApiResponse;
import com.foodify.backend_foodify.Entities.Cart;
import com.foodify.backend_foodify.Entities.Cart_Restaurent;
import com.foodify.backend_foodify.Service.CartService;

@RestController

public class CartController {

    @Autowired
    private CartService cart_service;

    @GetMapping("user/{userid}/cart")
    public ResponseEntity<ApiResponse<Page<Cart_Restaurent>>> getCart(@PathVariable Long userid,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Page<Cart_Restaurent> crt = cart_service.getUserCart(userid, page, size);
        ApiResponse<Page<Cart_Restaurent>> response = new ApiResponse<>();
        response.setData(crt);
        response.setMessage("All cart data fetch successfull");
        response.setSuccess(true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
