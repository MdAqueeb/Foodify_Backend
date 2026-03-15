package com.foodify.backend_foodify.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodWithRestaurentDTO {
    private Long food_id;
    private String food_name;
    private String food_description;
    private Double food_price;
    private Integer timeTake;
    private String food_picture;
    private String publicId;
    private Boolean isAvailable;
    // private Boolean isAvailable;
    private Long restaurant_id;
    private String restaurent_name;
}
