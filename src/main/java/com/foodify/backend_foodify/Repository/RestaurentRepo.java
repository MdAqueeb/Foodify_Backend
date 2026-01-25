package com.foodify.backend_foodify.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodify.backend_foodify.Entities.Restaurent;

@Repository
public interface RestaurentRepo extends JpaRepository<Restaurent, Long> {
    
}
