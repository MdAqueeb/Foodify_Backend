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

    @Query(
        value = "SELECT * FROM foods WHERE menu_id = :menuId AND popularity = :popular", 
        countQuery = "SELECT COUNT(*) FROM foods WHERE menu_id = :menuId AND popularity = :popular",
        nativeQuery = true
    )
    Page<Food> findByRestaurentMenu(@Param("menuId") Long menuId, @Param("popular") Boolean popular, Pageable pge);

    @Query(
        value = "SELECT * FROM foods WHERE menu_id = :menuId ", 
        countQuery = "SELECT COUNT(*) FROM foods WHERE menu_id = :menuId ",
        nativeQuery = true
    )
    Page<Food> findByRestaurentMenuFoods(@Param("menuId") Long menuId , Pageable pge);

    @Query(
        value = "SELECT * FROM foods WHERE menu_id = :menuId AND category = :food_category", 
        countQuery = "SELECT COUNT(*) FROM foods WHERE menu_id = :menuId AND category = :food_category",
        nativeQuery = true
    )
    Page<Food> findByRestaurentMenuCategory(@Param("food_category")String food_category,@Param("menuId") Long menuId, Pageable pge);


    @Query(
        value = "SELECT * FROM foods WHERE availability = true AND food_name LIKE CONCAT('%', :search, '%')", 
        countQuery = "SELECT COUNT(*) FROM foods WHERE availability = true AND food_name LIKE CONCAT('%', :search, '%')",
        nativeQuery = true
    )
    Page<Food> getSearchItems(@Param("search") String search, Pageable pge);
    
}
