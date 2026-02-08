package com.foodify.backend_foodify.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.foodify.backend_foodify.Entities.Cart_Restaurent;

@Repository
public interface CartRestaurentRepo extends JpaRepository<Cart_Restaurent, Long> {
    @Query(
        value = "SELECT * FROM cart_restaurent WHERE cart_id = :cartId AND restaurant_id = :restaurantId",
        nativeQuery = true
    )
    Optional<Cart_Restaurent> findByCart_Cart_idAndRestaurent_Restaurant_id(
            @Param("cartId") Long cartId,
            @Param("restaurantId") Long restaurantId
    );


    @Query(value = "SELECT * FROM cart_restaurent WHERE cart_id = :cart_id", 
        countQuery = "SELECT COUNT(*) FROM cart_restaurent WHERE cart_id = :cart_id",
        nativeQuery = true
    )
    Page<Cart_Restaurent> getCartRestaurent(@Param("cart_id") Long cart_id, Pageable pge);

    // @Query("")
    // public Cart_Restaurent createCartRestaurentById(Long restaurant_id);

}
