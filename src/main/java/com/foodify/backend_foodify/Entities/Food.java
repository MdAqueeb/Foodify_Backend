package com.foodify.backend_foodify.Entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "foods")
@Builder
@Data 
@NoArgsConstructor
@AllArgsConstructor
public class Food {
    
    @Id
    @Column(name = "food_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long food_id;

    @Column(name = "food_name", nullable = false)
    @NotBlank
    private String food_name;

    @Column(name = "food_description", nullable = false)
    @NotBlank
    private String food_description;

    @Column(name = "price", nullable = false)
    @NotNull
    private Double food_price;

    @Column(name = "availability", nullable = false)
    @NotNull
    @Builder.Default
    private Boolean isAvailable = true;

    @Column(name = "rating", nullable = false)
    @NotNull
    @Builder.Default
    private Double food_rating = 0.0;

    @Column(name = "popularity", nullable = false)
    @NotNull
    @Builder.Default
    private Boolean food_popularity = false;

    @Column(name = "min_time_taken")
    private Integer timeTake;


    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    @Builder.Default
    private Category food_category = Category.veg;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    @Builder.Default
    private FoodType food_type = FoodType.dessert;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(name = "food_time", nullable = false)
    private FoodTime foodtime = FoodTime.others;

    @Column(name = "created_at", nullable = false)
    @Builder.Default
    private LocalDateTime created_at = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    @JsonIgnore
    private Menu menu;

    enum FoodType{
        starter, 
        mains, 
        dessert
    }

    enum Category{
        veg, 
        non_veg, 
        gluten_free
    }

    enum FoodTime{
        breakfast, 
        lunch, 
        dinner, 
        others
    }

    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Food_Extra> food_extra;

    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Cart_Item> cartItems;

    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Order_item> orderItem;

    @PrePersist
    void assignValues() {
        food_extra = new ArrayList<>();
        cartItems = new ArrayList<>();
        orderItem = new ArrayList<>();
    }
}
