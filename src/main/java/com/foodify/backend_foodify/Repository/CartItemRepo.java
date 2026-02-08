package com.foodify.backend_foodify.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.foodify.backend_foodify.Entities.Cart_Item;

@Repository
public interface CartItemRepo extends JpaRepository<Cart_Item, Long>{
    @Query(
        value = "SELECT * FROM cart_items ci " +
                "WHERE ci.cart_restaurent_id = :cartRestId " +
                "AND ci.food_id = :foodId",
        nativeQuery = true
    )
    Cart_Item findByCart_restaurent_Cart_restaurent_idAndFood_Food_id(
        @Param("cartRestId") Long cartRestId,
        @Param("foodId") Long foodId
    );

    @Query(value = "SELECT * FROM cart_items WHERE cart_restaurent_id = :cartRestaurentId", 
        countQuery = "SELECT COUNT(*) FROM cart_items WHERE cart_restaurent_id = :cartRestaurentId",
        nativeQuery = true
    )
    Page<Cart_Item> getCartRestaurentItems(@Param("cartRestaurentId") Long cartRestaurentId, Pageable pge);
}
