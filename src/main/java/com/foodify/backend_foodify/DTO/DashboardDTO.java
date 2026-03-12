package com.foodify.backend_foodify.DTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.foodify.backend_foodify.DTO.SalesTrendDTO;
@Data
@NoArgsConstructor  
@AllArgsConstructor
public class DashboardDTO {
    private Double todaySales;
    private Long activeOrders;
    private Long totalRestaurants;
    private Long totalPendingOrders;
    private List<SalesTrendDTO> salesLast7Days;
}