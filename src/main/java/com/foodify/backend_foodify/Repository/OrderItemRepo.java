package com.foodify.backend_foodify.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.foodify.backend_foodify.Entities.Order_item;

@Repository
public interface OrderItemRepo extends JpaRepository<Order_item, Long> {
    
    @Query(
        value = "SELECT * FROM order_items WHERE order_id = :orderId",
        nativeQuery = true
    )
    List<Order_item> findByOrderOrder_id(@Param("orderId") Long orderId);
}
