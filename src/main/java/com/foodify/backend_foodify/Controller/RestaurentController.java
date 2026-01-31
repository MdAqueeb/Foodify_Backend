package com.foodify.backend_foodify.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.foodify.backend_foodify.DTO.ApiResponse;
import com.foodify.backend_foodify.Entities.Restaurent;
import com.foodify.backend_foodify.Service.RestaurentService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.autoconfigure.web.DataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
public class RestaurentController {

    @Autowired
    private RestaurentService rst_service;

    @GetMapping("/restaurents")
    public ResponseEntity<ApiResponse<Page<Restaurent>>> getRestaurentsByStatus(@RequestParam String status, @RequestParam int page, @RequestParam int size) {
        Page<Restaurent> rsts = rst_service.getRestaurentsByStatus(status, page, size);
        ApiResponse<Page<Restaurent>> response = new ApiResponse<>();
        response.setData(rsts);
        response.setMessage("All restaurtent fetch by using status");
        response.setSuccess(true);
        return new ResponseEntity<>(response, HttpStatus.OK);    
    }

    @PostMapping("/{userId}/addrestaurent")
    public ResponseEntity<ApiResponse<Restaurent>> createRestaurent(@RequestBody Restaurent restaurent, @PathVariable Long userId) {
        Restaurent rst = rst_service.createRst(restaurent, userId);
        ApiResponse<Restaurent> response = new ApiResponse<>();
        response.setData(rst);
        response.setMessage("restaurtent created successfully");
        response.setSuccess(true);
        return new ResponseEntity<>(response, HttpStatus.CREATED);   
        
    }
    
    
}
