package com.foodify.backend_foodify.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodify.backend_foodify.DTO.AddToCartDTO;
import com.foodify.backend_foodify.Entities.Cart;
import com.foodify.backend_foodify.Entities.Food;
import com.foodify.backend_foodify.Entities.Menu;
import com.foodify.backend_foodify.Entities.Restaurent;
import com.foodify.backend_foodify.Entities.User;
import com.foodify.backend_foodify.Exceptions.ResourceNotFoundException;
import com.foodify.backend_foodify.Repository.CartRepo;
import com.foodify.backend_foodify.Repository.FoodRepo;
import com.foodify.backend_foodify.Repository.RestaurentRepo;
import com.foodify.backend_foodify.Repository.UserRepo;

@Service
public class CartService {

    @Autowired
    UserRepo user_Repo;

    @Autowired
    FoodRepo food_Repo;

    // @Autowired
    // RestaurentRepo restaurent_repo;

    @Autowired
    CartRepo cart_repo;

    

    public Cart addToCart(Long userid, AddToCartDTO addCart) {
        Optional<User> usr = user_Repo.findById(userid);
        if(!usr.isPresent()){
            throw new ResourceNotFoundException("User Not Found");
        }

        Long food_id = addCart.getFoodId();
        Optional<Food> dish = food_Repo.findById(food_id);
        if(!dish.isPresent()){
            throw new ResourceNotFoundException("Food Not Found");
        }

        Food food = dish.get();

        Menu menu =  food.getMenu();
        Restaurent restaurent = menu.getRestaurent(); 

        User user = usr.get();

        Cart cart = user.getCart();
        if(cart == null){
            cart = createCart(userid);
        }

        
    }   

    public Cart createCart(Long userid){
        Optional<User> usr = user_Repo.findById(userid);
        if(!usr.isPresent()){
            throw new ResourceNotFoundException("User Not Found");
        }
        Cart crt = new Cart(); 
        User user = usr.get();
        crt.setUser(user);
        return cart_repo.save(crt);
    }
    
}
