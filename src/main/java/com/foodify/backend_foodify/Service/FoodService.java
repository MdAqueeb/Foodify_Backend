package com.foodify.backend_foodify.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodify.backend_foodify.Entities.Food;
import com.foodify.backend_foodify.Exceptions.ResourceNotFoundException;
import com.foodify.backend_foodify.Repository.FoodRepo;

@Service
public class FoodService {

    @Autowired
    private FoodRepo foodRepo;

    public Food getFoodbyId(Long food_id) {
        Optional<Food> food = foodRepo.findById(food_id);
        if(!food.isPresent()){
            throw new ResourceNotFoundException("Food Not Found");
        }
        return food.get();
    }
    
}
