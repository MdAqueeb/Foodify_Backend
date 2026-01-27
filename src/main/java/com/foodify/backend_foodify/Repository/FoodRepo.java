package com.foodify.backend_foodify.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.foodify.backend_foodify.Entities.Food;

@Repository
public interface FoodRepo extends JpaRepository<Food, Long>{


    
}
