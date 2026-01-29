package com.foodify.backend_foodify.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.foodify.backend_foodify.DTO.ApiResponse;
import com.foodify.backend_foodify.Entities.Restaurent;
import com.foodify.backend_foodify.Service.RestaurentService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController

public class RestaurentController {
    
    @Autowired
    private RestaurentService restaurent_service;

    @GetMapping("/restaurents")
    public ResponseEntity<ApiResponse<List<Restaurent>>> getMethodName(@RequestParam int page) {
        List<Restaurent> restaurents = restaurent_service.getallRestaurents(page);
        ApiResponse<List<Restaurent>> response = new ApiResponse<>();
        response.setData(restaurents);
        response.setMessage("page "+page+" all restaurents fetch successfull");
        response.setSuccess(true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}
