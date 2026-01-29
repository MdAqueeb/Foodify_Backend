package com.foodify.backend_foodify.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodify.backend_foodify.Entities.Menu;

@Repository
public interface MenuRepo extends JpaRepository<Menu, Long>{
    
}
