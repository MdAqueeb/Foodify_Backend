package com.foodify.backend_foodify.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodify.backend_foodify.Entities.Cart;
import com.foodify.backend_foodify.Entities.Cart_Restaurent;
import com.foodify.backend_foodify.Entities.Restaurent;

@Repository
public interface CartRestaurentRepo
        extends JpaRepository<Cart_Restaurent, Long> {

    Optional<Cart_Restaurent> findByCartAndRestaurent(
            Cart cart,
            Restaurent restaurent
    );
}

