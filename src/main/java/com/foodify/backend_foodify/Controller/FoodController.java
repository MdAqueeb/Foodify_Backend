package com.foodify.backend_foodify.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.foodify.backend_foodify.DTO.ApiResponse;
import com.foodify.backend_foodify.Entities.Food;
import com.foodify.backend_foodify.Service.FoodService;

@RestController
@RequestMapping("/restaurent/{restaurentid}/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @GetMapping("/{foodId}")
    public ResponseEntity<ApiResponse<Food>> getFood(
            @PathVariable Long restaurentid,
            @PathVariable Long foodId) {

        Food food = foodService.getFoodbyId(foodId, restaurentid);

        return ResponseEntity.ok(
                new ApiResponse<>(true, food, "Food fetched successfully")
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Food>>> getFoods(
            @PathVariable Long restaurentid) {

        List<Food> foods = foodService.getFoodByRestaurent(restaurentid);

        return ResponseEntity.ok(
                new ApiResponse<>(true, foods, "Food list fetched")
        );
    }
}
