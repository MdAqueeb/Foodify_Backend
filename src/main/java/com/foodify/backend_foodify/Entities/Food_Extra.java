package com.foodify.backend_foodify.Entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
// import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "food_extras")
public class Food_Extra{

    @Id
    @Column(name = "food_extras_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foodExtra_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id", nullable = false)
    @NotNull
    private Food food;

    @Column(name = "isAvaliable", nullable = false)
    @Builder.Default
    private Boolean availability = true;

    @Column(name = "food_extra_quantity", nullable = false)
    @Builder.Default
    private Integer quantity = 1;

    @Column(name = "food_extra_item", nullable = false)
    @NotNull
    private String item;

    @Column(name = "food_extra_price", nullable = false)
    @NotNull
    private Double item_price;

    @OneToMany(mappedBy = "food_extra", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Cart_items_Extra> cart_item_extra;
}