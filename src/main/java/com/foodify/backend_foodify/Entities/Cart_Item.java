package com.foodify.backend_foodify.Entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cart_items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart_Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long cartItemId;

    @Column(nullable = false)
    @Builder.Default
    private Integer quantity = 1;

    @Column(nullable = false)
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_restaurent_id", nullable = false)
    @JsonIgnore
    private Cart_Restaurent cartRestaurent;   // ✅ RENAMED

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;

    @OneToMany(
        mappedBy = "cart_item",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @Builder.Default
    @JsonIgnore
    private List<Cart_items_Extra> cart_item_extra = new ArrayList<>();

    @Column(name = "special_instructions")
    private String specialInstructions;
}
