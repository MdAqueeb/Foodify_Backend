package com.foodify.backend_foodify.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.foodify.backend_foodify.Entities.Cart_Restaurent;

@Repository
public interface CartRestaurentRepo extends JpaRepository<Cart_Restaurent, Long> {
    @Query(
        value = "SELECT * FROM cart_restaurent " +
                "WHERE cart_id = :cartId AND restaurant_id = :restaurantId",
        nativeQuery = true
    )
    Optional<Cart_Restaurent> findByCart_Cart_idAndRestaurent_Restaurant_id(
            @Param("cartId") Long cartId,
            @Param("restaurantId") Long restaurantId
    );

    // @Query("")
    // public Cart_Restaurent createCartRestaurentById(Long restaurant_id);

}
