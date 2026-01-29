package com.foodify.backend_foodify.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodify.backend_foodify.Entities.Order_item_extra;

@Repository
public interface Order_Item_Extra_Repo extends JpaRepository<Order_item_extra, Long> {
    
}
