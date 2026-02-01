package com.foodify.backend_foodify.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.foodify.backend_foodify.Entities.Food;

@Repository
public interface FoodRepo extends JpaRepository<Food, Long>{

    @Query(
        value = "SELECT * FROM foods WHERE menu_id = :menuId AND food_type = :type", 
        countQuery = "SELECT COUNT(*) FROM foods WHERE menu_id = :menuId AND food_type = :type",
        nativeQuery = true
    )
    Page<Food> findByRestaurentMenu(@Param("type") String type, @Param("menuId") Long menuId, Pageable pge);

    
    
}
