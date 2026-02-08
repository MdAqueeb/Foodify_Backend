package com.foodify.backend_foodify.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.foodify.backend_foodify.DTO.ApiResponse;
import com.foodify.backend_foodify.Entities.Cart_Item;
import com.foodify.backend_foodify.Entities.Cart_Restaurent;
import com.foodify.backend_foodify.Service.CartRestaurentService;

@RestController
public class CartRestaurentController {
    
    @Autowired private CartRestaurentService crt_src;

    @GetMapping("user/{userid}/cart/{cartRestaurentId}")
    public ResponseEntity<ApiResponse<Page<Cart_Item>>> getCartItems(@PathVariable Long userid, @PathVariable Long cartRestaurentId,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Page<Cart_Item> crt = crt_src.getAllItems(userid, cartRestaurentId, page, size);
        ApiResponse<Page<Cart_Item>> response = new ApiResponse<>();
        response.setData(crt);
        response.setMessage("All cart data fetch successfull");
        response.setSuccess(true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
