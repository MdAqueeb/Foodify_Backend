package com.foodify.backend_foodify.DTO;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data   
@NoArgsConstructor
@AllArgsConstructor
public class EarningDTO {
    private Double totalSales;
    private Long totalOrders;
    private Double totalProfit;
}
