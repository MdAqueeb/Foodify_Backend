package com.foodify.backend_foodify.Entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "wishlist_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wishlist_item {
    
    @Id
    @Column(name = "wishlist_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishlist_id;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_type", nullable = false)
    @NotNull
    private Item_type item_type;

    @Column(name = "item_id", nullable = false)
    @NotNull
    private Long item_id;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime created_at;

    enum Item_type{
        food, 
        restaurent
    }

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "wishlist_id", nullable = false)
    private Wishlist wishlist;

    @PrePersist
    void assign_values() {
        created_at = LocalDateTime.now();
    }
}
