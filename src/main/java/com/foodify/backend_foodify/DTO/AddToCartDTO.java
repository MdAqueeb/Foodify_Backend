package com.foodify.backend_foodify.DTO;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddToCartDTO {
    private Long foodId;
    private Integer quantity;
    private String specialInstructions;
    @Builder.Default
    private List<Long> addons = new ArrayList<>();

    // @PrePersist
    // void assignValues() {
    //     addons = new ArrayList<>();
    // }

}
