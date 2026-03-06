package com.foodify.backend_foodify.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.foodify.backend_foodify.Entities.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
    
    // fetch orders for a user excluding a specific status
    @Query(
        value = "SELECT * FROM orders WHERE user_id = :userId AND status != :status", 
        nativeQuery = true
    )
    List<Order> findByUserUserIdAndOrderStatusNot(@Param("userId") Long userId, @Param("status") String status);

    // fetch orders for a user with a specific status
    @Query(
        value = "SELECT * FROM orders WHERE user_id = :userId AND status = :status", 
        nativeQuery = true
    )
    List<Order> findByUserUserIdAndOrderStatus(@Param("userId") Long userId, @Param("status") Order.OrderStatus status);

    // fetch orders for a user with any of the provided statuses
    @Query(
        value = "SELECT * FROM orders WHERE user_id = :userId AND status IN :statuses", 
        nativeQuery = true
    )
    List<Order> findByUserUserIdAndOrderStatusIn(@Param("userId") Long userId, @Param("statuses") List<Order.OrderStatus> statuses);
}
