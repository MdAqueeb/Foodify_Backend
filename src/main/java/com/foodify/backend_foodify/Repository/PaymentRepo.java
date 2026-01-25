package com.foodify.backend_foodify.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodify.backend_foodify.Entities.Payment;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long>{
    
}
