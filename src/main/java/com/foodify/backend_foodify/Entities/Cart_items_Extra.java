package com.foodify.backend_foodify.Entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "cart_item_extras")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart_items_Extra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cart_item_extras_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_item_id", nullable = false)
    private Cart_Item cart_item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_extra_id", nullable = false)
    private Food_Extra food_extra;

    @Column(name = "price", nullable = false)
    private Double price;

    
}


// @Entity
// @Table(name = "cart_item_extras")
// @Data
// @NoArgsConstructor
// @AllArgsConstructor
// public class Cart_items_Extra {
    
//     @Id
//     @Column(name = "cart_item_extras_id")
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long cart_item_extras_id;

//     @Column(name = "price", nullable = false)
//     @NotNull
//     private Double totalAmount;

//     @ManyToOne(fetch = FetchType.LAZY)
//     @JoinColumn(name = "cart_item_id", nullable = false)
//     private Cart_Item cart_item;

//     @ManyToOne(fetch = FetchType.LAZY)
//     @JoinColumn(name = "extras_id", nullable = false)
//     private Extras extras;
// }
