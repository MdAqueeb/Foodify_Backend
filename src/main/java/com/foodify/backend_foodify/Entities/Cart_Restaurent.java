package com.foodify.backend_foodify.Entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@Entity
@Table(name = "cart_restaurent")
@NoArgsConstructor
@AllArgsConstructor
public class Cart_Restaurent {
    @Id
    @Column(name = "cart_restaurent_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cart_restaurent_id;

    @Column(name = "total_amount", nullable = false)
    private Double total_amount;

    @Column(name = "total_items", nullable = false)
    @Builder.Default
    private Integer total_items = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @OneToMany(mappedBy = "cart_restaurent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart_Item> cart_Items;


    @PrePersist
    void assignValues() {
        cart_Items = new ArrayList<>();
    }
}
