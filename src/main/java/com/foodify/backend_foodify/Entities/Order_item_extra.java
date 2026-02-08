package com.foodify.backend_foodify.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order_item_extra {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_extras_id")
    private Long order_item_extra_id;

    @Column(name = "price", nullable = false)
    @NotNull
    private Double total_amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_id", nullable = false)
    private Order_item order_item;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_extra_id", nullable = false)
    private Food_Extra food_extra;


    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "extras_id", nullable = false)
    // private Extras extras;

}
