package com.foodify.backend_foodify.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.foodify.backend_foodify.Entities.Cart;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {

    @Query(value = "SELECT * FROM cart WHERE user_id = :userid", nativeQuery = true)
    Cart findByUserId(@Param("userid") Long userid);
    
}
