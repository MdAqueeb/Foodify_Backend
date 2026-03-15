package com.foodify.backend_foodify.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.foodify.backend_foodify.Entities.Order;
import com.foodify.backend_foodify.Entities.Order.OrderStatus;
import com.foodify.backend_foodify.DTO.SalesTrendDTO;

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
    List<Order> findByUserUserIdAndOrderStatus(@Param("userId") Long userId, @Param("status") String status);

    // fetch orders for a user with any of the provided statuses
    @Query(
        value = "SELECT * FROM orders WHERE user_id = :userId AND status IN :statuses", 
        nativeQuery = true
    )
    List<Order> findByUserUserIdAndOrderStatusIn(@Param("userId") Long userId, @Param("statuses") List<String> statuses);
    @Query(
        value = "SELECT * FROM orders WHERE restaurent_id = :restaurentId AND status != :status", 
        nativeQuery = true
    )
    List<Order> findByRestaurentIdAndOrderStatusNot(@Param("restaurentId") Long restaurentId, @Param("status") String status);

    @Query(
        value = "SELECT * FROM orders WHERE restaurent_id = :restaurentId AND status IN :statuses", 
        nativeQuery = true
    )
    List<Order> findByRestaurentIdAndOrderStatusIn(@Param("restaurentId") Long restaurentId, @Param("statuses") List<String> statuses);    
    // List<Order> findByRestaurentIdAndOrderStatusIn(Long restaurentId, List<OrderStatus> historyStatuses);

    @Query(
        value = "SELECT * FROM orders WHERE restaurent_id = :restaurentId AND status = :status", 
        nativeQuery = true
    )
    List<Order> countByRestaurentUserUserId(@Param("restaurentId") Long restaurentId, @Param("status") String status);

    @Query(
        value = "SELECT COALESCE(SUM(total_amount), 0) FROM orders WHERE restaurent_id IN (:restaurentIds) AND status = 'deliverd' AND DATE(created_at) = CURRENT_DATE", 
        nativeQuery = true
    )
    Double getTodaySales(@Param("restaurentIds") List<Long> restaurentIds);

    @Query(
        value = "SELECT COUNT(*) FROM orders WHERE restaurent_id IN (:restaurentIds) AND status IN ('cooking', 'on_the_way')", 
        nativeQuery = true
    )
    Long countActiveOrders(@Param("restaurentIds") List<Long> restaurentIds);

    @Query(
        value = "SELECT COUNT(*) FROM orders WHERE restaurent_id IN (:restaurentIds) AND status = 'created'", 
        nativeQuery = true
    )
    Long countPendingOrders(@Param("restaurentIds") List<Long> restaurentIds);

    @Query(
        value = """
        SELECT DATE_FORMAT(d.day, '%a') as day, 
            COALESCE(SUM(o.total_amount), 0) as total 
        FROM (
            SELECT CURDATE() - INTERVAL 6 DAY as day
            UNION SELECT CURDATE() - INTERVAL 5 DAY
            UNION SELECT CURDATE() - INTERVAL 4 DAY
            UNION SELECT CURDATE() - INTERVAL 3 DAY
            UNION SELECT CURDATE() - INTERVAL 2 DAY
            UNION SELECT CURDATE() - INTERVAL 1 DAY
            UNION SELECT CURDATE()
        ) d 
        LEFT JOIN orders o
        ON DATE(o.created_at) = d.day 
        AND o.restaurent_id IN (:restaurentIds) 
        AND o.status = 'deliverd' 
        GROUP BY d.day 
        ORDER BY d.day
        """, // "SELECT COALESCE(SUM(total_amount), 0) FROM orders WHERE restaurent_id IN (:restaurentIds) AND status = 'deliverd' AND created_at >= CURRENT_DATE - INTERVAL 7 days GROUP BY DATE(created_at) ORDER BY DATE(created_at)""";,
        nativeQuery = true
    )
    List<SalesTrendDTO> getLast7DaysSales(@Param("restaurentIds") List<Long> restaurentIds);
}
