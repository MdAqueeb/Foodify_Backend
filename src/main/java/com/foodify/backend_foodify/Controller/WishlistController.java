package com.foodify.backend_foodify.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.foodify.backend_foodify.DTO.ApiResponse;
import com.foodify.backend_foodify.Entities.Wishlist_item;
import com.foodify.backend_foodify.Service.WishlistService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class WishlistController {
    
    @Autowired 
    private WishlistService wistlistService;

    @PostMapping("{userid}/wishlist/add")
    public ResponseEntity<ApiResponse<Wishlist_item>> addWishlistItem(@RequestBody Wishlist_item itm, @PathVariable Long userid){
        Wishlist_item item = wistlistService.addItem(itm, userid);
        ApiResponse<Wishlist_item> response = new ApiResponse<>();
        response.setData(item);
        response.setMessage("Successfully Added");
        response.setSuccess(true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("{userid}/wishlist")
    public ResponseEntity<ApiResponse<List<Wishlist_item>>> getWishlist( @PathVariable Long userid, @RequestParam(required = false) String fvtType){
        List<Wishlist_item> item;
        if (fvtType == null) {
            item = wistlistService.getItems(userid);
        } else {
            item = wistlistService.getItems(userid, fvtType);
        }
        ApiResponse<List<Wishlist_item>> response = new ApiResponse<>();
        response.setData(item);
        response.setMessage("Successfully Added");
        response.setSuccess(true);
        return new ResponseEntity<>(response, HttpStatus.OK);


    }

    @DeleteMapping("{userid}/wishlist/{wishlistItemId}")
    public ResponseEntity<ApiResponse<Wishlist_item>> deleteWishlistItem( @PathVariable Long userid, @PathVariable Long wishlistItemId){
        Wishlist_item item = wistlistService.removeItem(userid, wishlistItemId);
        ApiResponse<Wishlist_item> response = new ApiResponse<>();
        response.setData(item);
        response.setMessage("Successfully Added");
        response.setSuccess(true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
