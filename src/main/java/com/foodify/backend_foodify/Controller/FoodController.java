package com.foodify.backend_foodify.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
// import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.foodify.backend_foodify.DTO.ApiResponse;
import com.foodify.backend_foodify.Entities.Food;
import com.foodify.backend_foodify.Service.FoodService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/food")
public class FoodController {
    // get specific food

    @Autowired
    private FoodService foodService;

    @GetMapping("/{food_id}")
    public ResponseEntity<ApiResponse<Food>> getSpecificFood(@PathVariable Long food_id) throws Exception{
        Food food = foodService.getFoodbyId(food_id);
        ApiResponse<Food> response = new ApiResponse<>(true, food, "Food fetch successfull");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
