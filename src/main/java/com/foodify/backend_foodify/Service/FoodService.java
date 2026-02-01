package com.foodify.backend_foodify.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.foodify.backend_foodify.Entities.Food;
import com.foodify.backend_foodify.Entities.Menu;
import com.foodify.backend_foodify.Entities.Restaurent;
import com.foodify.backend_foodify.Entities.Food.FoodType;
import com.foodify.backend_foodify.Exceptions.ResourceConflictException;
import com.foodify.backend_foodify.Exceptions.ResourceNotFoundException;
import com.foodify.backend_foodify.Repository.FoodRepo;
import com.foodify.backend_foodify.Repository.MenuRepo;
import com.foodify.backend_foodify.Repository.RestaurentRepo;

@Service
public class FoodService {

    @Autowired
    private FoodRepo foodRepo;

    @Autowired
    private RestaurentRepo restaurent_repo;

    @Autowired
    private MenuRepo menu_repo;

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

    public Page<Food> getFoodByRestaurent_Menu(Long restaurentId, Long menuId, String type, int page, int size) {
        Optional<Restaurent> restaurent = restaurent_repo.findById(restaurentId);
        if(!restaurent.isPresent()){
            throw new ResourceNotFoundException("Restaurent Not Found");
        }

        Optional<Menu> menu = menu_repo.findById(menuId);
        if(!menu.isPresent()){
            throw new ResourceNotFoundException("Menu Not Found");
        }

        if(restaurent.get().getMenu() == null){
            throw new ResourceNotFoundException("The Restaurent Menu is Not There");
        }
        else if(!(restaurent.get().getMenu().getMenu_id().equals(menuId)) ){
            throw new ResourceConflictException("Restaurent Menu and given menu not Match");
        }
        else if(!((type.equals(FoodType.dessert.name()) || type.equals(FoodType.mains.name()) || type.equals(FoodType.starter.name())))){
            throw new ResourceNotFoundException("The given type Not Found");
        }

        Pageable pge = PageRequest.of(page, size);

        return foodRepo.findByRestaurentMenu(type, menuId, pge);
    }

    public Food changeFoodAvailability(Long restaurentId, Long menuId, Long foodId, Boolean available) {
        Optional<Restaurent> restaurent = restaurent_repo.findById(restaurentId);
        if(!restaurent.isPresent()){
            throw new ResourceNotFoundException("Restaurent Not Found");
        }

        Optional<Menu> menu = menu_repo.findById(menuId);
        if(!menu.isPresent()){
            throw new ResourceNotFoundException("Menu Not Found");
        }
        Long menu_id = menu.get().getMenu_id();

        Optional<Food> food = foodRepo.findById(foodId);
        if(!food.isPresent()){
            throw new ResourceNotFoundException("Food Not Found");
        }

        if(available == null){
            throw new ResourceConflictException("Availbale value Error");
        }

        if(restaurent.get().getMenu() == null){
            throw new ResourceNotFoundException("The Restaurent Menu is Not There");
        }
        else if(!(restaurent.get().getMenu().getMenu_id().equals(menuId)) ){
            throw new ResourceConflictException("Restaurent Menu and given menu not Match");
        }
        else if(!(food.get().getMenu().getMenu_id().equals(menu_id))){
            throw new ResourceConflictException("Given menu id and food menu id not match");
        }

        Food fd = food.get();
        fd.setIsAvailable(available);
        return foodRepo.save(fd);
    }
    
}
