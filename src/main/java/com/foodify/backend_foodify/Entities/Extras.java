package com.foodify.backend_foodify.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "extras")
@Data 
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Extras {
    
    @Id
    @Column(name = "extras_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long extras_id;

    @Column(name = "name", nullable = false)
    @NotBlank
    private String extra_name;

    @Column(name = "instructions", columnDefinition = "TEXT")
    private String extra_instructions;

    @Column(name = "quantity", nullable = false)
    @Builder.Default
    private Integer quantity = 1;

    @Column(name = "price", nullable = false)
    @NotNull
    private Double item_price;
}
