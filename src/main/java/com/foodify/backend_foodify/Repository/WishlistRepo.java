package com.foodify.backend_foodify.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.foodify.backend_foodify.Entities.Wishlist;

@Repository
public interface WishlistRepo extends JpaRepository<Wishlist, Long> {

    @Query(value = "select * from wishlists where user_id = :user_id" , nativeQuery = true) 
    Optional<Wishlist> findByUser_User_id( @Param("user_id") Long userId);
}

