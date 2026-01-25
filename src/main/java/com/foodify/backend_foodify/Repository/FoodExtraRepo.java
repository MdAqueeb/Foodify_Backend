package com.foodify.backend_foodify.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodify.backend_foodify.Entities.Food_Extra;

@Repository
public interface FoodExtraRepo extends JpaRepository<Food_Extra, Long>{
    
}