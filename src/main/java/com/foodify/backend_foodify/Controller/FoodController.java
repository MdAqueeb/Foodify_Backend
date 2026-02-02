package com.foodify.backend_foodify.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
// import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import com.foodify.backend_foodify.DTO.ApiResponse;
import com.foodify.backend_foodify.Entities.Food;
import com.foodify.backend_foodify.Service.FoodService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("")
@Validated
public class FoodController {
    // get specific food

    @Autowired
    private FoodService foodService;

    @GetMapping("restaurent/{restaurentid}/food/{food_id}")
    public ResponseEntity<ApiResponse<Food>> getSpecificFood(@PathVariable Long food_id, @PathVariable Long restaurentid) throws Exception{
        Food food = foodService.getFoodbyId(food_id, restaurentid);
        ApiResponse<Food> response = new ApiResponse<>(true, food, "Food fetch successfull");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // specific restaurent food items
    @GetMapping("restaurent/{restaurentid}/food")
    public ResponseEntity<ApiResponse<List<Food>>> getRestaurentFoods(@PathVariable Long restaurentid){
        List<Food> foods = foodService.getFoodByRestaurent(restaurentid);
        ApiResponse<List<Food>> response = new ApiResponse<>(true, foods, "List of food fetch successfull");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("restaurent/{restaurentId}/Menu/{menuId}")
    // add param food category
    public ResponseEntity<ApiResponse<Page<Food>>> getMethodName(@PathVariable Long restaurentId, @PathVariable Long menuId, @RequestParam String type, @RequestParam int page,@DefaultValue(value = "6") @RequestParam int size) {
        Page<Food> foods = foodService.getFoodByRestaurent_Menu(restaurentId, menuId, type, page, size);
        ApiResponse<Page<Food>> response = new ApiResponse<>();
        response.setData(foods);
        response.setMessage("List of Food Fetch Successfull");
        response.setSuccess(true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PatchMapping("restaurent/{restaurentId}/Menu/{menuId}/Food/{foodId}")
    public ResponseEntity<ApiResponse<Food>> changeAvailablility(@PathVariable Long restaurentId, @PathVariable Long menuId, @PathVariable Long foodId, @RequestParam Boolean available){
        Food food = foodService.changeFoodAvailability(restaurentId, menuId, foodId, available);
        ApiResponse<Food> response = new ApiResponse<>();
        response.setData(food);
        response.setMessage("Available Change Successfull");
        response.setSuccess(true);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping("restaurent/{restaurentId}/Menu/{menuId}")
    public ResponseEntity<ApiResponse<Food>> addFood(@PathVariable Long restaurentId, @PathVariable Long menuId, @RequestBody @Valid Food Food){
        Food food = foodService.createFood(restaurentId, menuId, Food);
        ApiResponse<Food> response = new ApiResponse<>();
        response.setData(food);
        response.setMessage("Food Added Successfull");
        response.setSuccess(true);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("restaurent/{restaurentId}/Menu/{menuId}/Food/{foodId}")
    public ResponseEntity<ApiResponse<Food>> modifyFoodDetails(@PathVariable Long restaurentId, @PathVariable Long menuId, @PathVariable Long foodId, @RequestBody Food foodDetails){
        Food food = foodService.updateFoodDetails(restaurentId, menuId, foodId, foodDetails);
        ApiResponse<Food> response = new ApiResponse<>();
        response.setData(food);
        response.setMessage("Food Details Updated");
        response.setSuccess(true);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}
