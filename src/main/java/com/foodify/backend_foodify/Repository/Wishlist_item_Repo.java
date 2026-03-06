package com.foodify.backend_foodify.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.foodify.backend_foodify.Entities.Wishlist_item;

@Repository
public interface Wishlist_item_Repo extends JpaRepository<Wishlist_item, Long>{
    
    @Query(value = "SELECT * FROM wishlist_items WHERE item_type = :item_type", nativeQuery = true)
    List<Wishlist_item> findByItemType(@Param("item_type") String item_type);

}
