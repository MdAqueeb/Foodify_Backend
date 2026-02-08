package com.foodify.backend_foodify.DTO;

import com.foodify.backend_foodify.Entities.Order.OrderStatus;
import com.foodify.backend_foodify.Entities.Order.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.foodify.backend_foodify.Entities.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusDTO {
    private Long userid;
    private OrderStatus orderStatus;      
    // private PaymentStatus paymentStatus;
}
