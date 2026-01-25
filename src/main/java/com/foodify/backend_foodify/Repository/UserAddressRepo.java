package com.foodify.backend_foodify.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodify.backend_foodify.Entities.User_Address;

@Repository
public interface UserAddressRepo extends JpaRepository<User_Address, Long>{
    
}
