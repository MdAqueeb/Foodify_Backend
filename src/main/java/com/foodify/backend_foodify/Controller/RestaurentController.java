package com.foodify.backend_foodify.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.foodify.backend_foodify.DTO.ApiResponse;
import com.foodify.backend_foodify.Entities.Restaurent;
import com.foodify.backend_foodify.Service.RestaurentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class RestaurentController {
    
    @Autowired
    private RestaurentService restaurentService;

    @GetMapping("/restaurent/{restaurentId}")
    public ResponseEntity<ApiResponse<Restaurent>> getMethodName(@PathVariable Long restaurentId) {
        Restaurent restaurent = restaurentService.findRestaurent(restaurentId);
        ApiResponse<Restaurent> response = new ApiResponse<>();
        response.setData(restaurent);
        response.setMessage("Restaurent fetch Successfully");
        response.setSuccess(true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}
