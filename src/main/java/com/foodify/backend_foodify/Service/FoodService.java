package com.foodify.backend_foodify.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodify.backend_foodify.Entities.Food;
import com.foodify.backend_foodify.Entities.Menu;
import com.foodify.backend_foodify.Entities.Restaurent;
import com.foodify.backend_foodify.Exceptions.ResourceNotFoundException;
import com.foodify.backend_foodify.Repository.FoodRepo;
import com.foodify.backend_foodify.Repository.RestaurentRepo;

@Service
public class FoodService {

    @Autowired
    private FoodRepo foodRepo;

    @Autowired
    private RestaurentRepo restaurent_repo;

    public Food getFoodbyId(Long food_id, Long restaurentid) {
        Optional<Food> food = foodRepo.findById(food_id);
        if(!food.isPresent()){
            throw new ResourceNotFoundException("Food Not Found");
        }
        Menu menu = food.get().getMenu();

        if(menu == null){
            throw new ResourceNotFoundException("Menu Not Found");
        }
        
        Restaurent restaurent = menu.getRestaurent();
        Optional<Restaurent> rst = restaurent_repo.findById(restaurentid);

        if(restaurent == null || !rst.isPresent()){
            throw new ResourceNotFoundException("Restaurent Not Found");
        }
        else if(!restaurent.getRestaurant_id().equals(restaurentid)){
            throw new ResourceNotFoundException("In this restaurent Food Not Found");
        }
        return food.get();
    }

    public List<Food> getFoodByRestaurent(Long restaurentid) {
        Optional<Restaurent> restaurent = restaurent_repo.findById(restaurentid);
        if(!restaurent.isPresent()){
            throw new ResourceNotFoundException("Restaurent Not Found");
        }
        Menu menu = restaurent.get().getMenu();
        if(menu == null){
            throw new ResourceNotFoundException("Menu Not Found");
        }
        List<Food> foods = menu.getFoods();
        return foods.stream()
                .limit(4)
                .collect(Collectors.toList());
    }
    
}
