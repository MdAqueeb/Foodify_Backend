package com.foodify.backend_foodify.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.foodify.backend_foodify.Entities.User_Address;

@Repository
public interface UserAddressRepo extends JpaRepository<User_Address, Long>{
    
    @Query(
        value = "SELECT * FROM user_addresses WHERE user_id = :userId", 
        nativeQuery = true
    )
    List<User_Address> findByUserUserId(@Param("userId") Long userId);
}
