package com.foodify.backend_foodify.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.foodify.backend_foodify.DTO.FoodWithRestaurentDTO;
import com.foodify.backend_foodify.Entities.Food;
import com.foodify.backend_foodify.Entities.Menu;
import com.foodify.backend_foodify.Entities.Restaurent;
import com.foodify.backend_foodify.Entities.Food.Category;
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

    public Food createFood(Long restaurentId, Long menuId, Food food) {
        Optional<Restaurent> restaurent = restaurent_repo.findById(restaurentId);
        if(!restaurent.isPresent()){
            throw new ResourceNotFoundException("Restaurent Not Found");
        }

        Optional<Menu> menu = menu_repo.findById(menuId);
        if(!menu.isPresent()){
            throw new ResourceNotFoundException("Menu Not Found");
        }

        if(restaurent.get().getMenu() == null){
            throw new ResourceConflictException("Given Restaurent Menu is Not there");
        }
        else if(!(restaurent.get().getMenu().getMenu_id().equals(menuId))){
            throw new ResourceConflictException("Restaurent Menu and given Menu Not Match");
        }
        
        food.setMenu(menu.get());
        return foodRepo.save(food);
    }

    public Food updateFoodDetails(Long restaurentId, Long menuId, Long foodId, Food foodDetails) {
        Optional<Restaurent> restaurent = restaurent_repo.findById(restaurentId);
        if(!restaurent.isPresent()){
            throw new ResourceNotFoundException("Restaurent Not Found");
        }

        Optional<Food> food = foodRepo.findById(foodId);
        if(!food.isPresent()){
            throw new ResourceNotFoundException("Food Not Found");
        }

        Optional<Menu> menu = menu_repo.findById(menuId);
        if(!menu.isPresent()){
            throw new ResourceNotFoundException("Menu Not Found");
        }

        if(restaurent.get().getMenu() == null){
            throw new ResourceConflictException("Given Restaurent Menu is Not there");
        }
        else if(!(restaurent.get().getMenu().getMenu_id().equals(menuId))){
            throw new ResourceConflictException("Restaurent Menu and given Menu Not Match");
        }
        if(food.get().getMenu() == null){
            throw new ResourceConflictException("Food not contain any Menu");
        }
        else if(!(food.get().getMenu().getMenu_id().equals(menuId))){
            throw new ResourceConflictException("Food Menu and given Menu Not Match");
        }

        Food Food = food.get();
        Food.setFood_name(foodDetails.getFood_name());
        Food.setFood_description(foodDetails.getFood_description());
        Food.setFood_price(foodDetails.getFood_price());
        Food.setIsAvailable(foodDetails.getIsAvailable());
        Food.setFood_rating(foodDetails.getFood_rating());
        Food.setFood_popularity(foodDetails.getFood_popularity());
        Food.setTimeTake(foodDetails.getTimeTake());
        Food.setFood_category(foodDetails.getFood_category());
        Food.setFood_type(foodDetails.getFood_type());
        Food.setFoodtime(foodDetails.getFoodtime());

        return foodRepo.save(Food);
    }

    public Page<Food> getPopularFoods(Long restaurentId, Long menuId, Boolean popular, int page, int size) {
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

        Pageable pge = PageRequest.of(page, size);
        Page<Food> foods;
        if(popular == null || popular == false){
            foods = foodRepo.findByRestaurentMenuFoods(menuId, pge);
        }
        else{
            foods = foodRepo.findByRestaurentMenu(menuId, popular, pge);
        }
        
        return foods;
    }

    public Page<Food> getCategoryFoods(Long restaurentId, Long menuId, String food_category, int page, int size) {
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

        if(!(Category.gluten_free.name().equals(food_category) || Category.non_veg.name().equals(food_category)
            || Category.veg.name().equals(food_category))){
            throw new ResourceConflictException("food_category not found");
        }

        Pageable pge = PageRequest.of(page, size);
        return foodRepo.findByRestaurentMenuCategory(food_category, menuId, pge);
    }

    public Page<FoodWithRestaurentDTO> getFoodBySearch(String search, int page, int size) {
        Pageable pge = PageRequest.of(page, size);
        Page<Food> food = foodRepo.getSearchItems(search, pge);
        List<FoodWithRestaurentDTO> dtoList = food.getContent().stream().map(fd -> {
        Restaurent rst = fd.getMenu().getRestaurent(); // fetch restaurant
        return new FoodWithRestaurentDTO(
            fd.getFood_id(),
            fd.getFood_name(),
            fd.getFood_description(),
            fd.getFood_price(),
            fd.getTimeTake(),
            fd.getFood_popularity(),
            // fd.getPicture(),
            rst.getRestaurant_id(),
            rst.getRestaurent_name()
        );
    }).collect(Collectors.toList());

    return new PageImpl<>(dtoList, pge, food.getTotalElements());
    }
    

}
