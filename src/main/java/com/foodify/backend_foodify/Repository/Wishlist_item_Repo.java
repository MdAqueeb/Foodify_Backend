package com.foodify.backend_foodify.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodify.backend_foodify.Entities.Wishlist_item;

@Repository
public interface Wishlist_item_Repo extends JpaRepository<Wishlist_item, Long>{
    
}
