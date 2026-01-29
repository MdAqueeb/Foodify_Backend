package com.foodify.backend_foodify.Entities;

// import java.util.ArrayList;
// import java.util.List;

// import jakarta.persistence.CascadeType;
// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.OneToMany;
// import jakarta.persistence.PrePersist;
// import jakarta.persistence.Table;
// import jakarta.validation.constraints.NotBlank;
// import jakarta.validation.constraints.NotNull;
// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.Data;
// import lombok.NoArgsConstructor;

// @Entity
// @Table(name = "extras")
// @Data 
// @NoArgsConstructor
// @AllArgsConstructor
// @Builder
// public class Extras {
    
//     @Id
//     @Column(name = "extras_id")
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long extras_id;

//     @Column(name = "name", nullable = false)
//     @NotBlank
//     private String extra_name;

//     @Column(name = "instructions", columnDefinition = "TEXT")
//     private String extra_instructions;

//     @Column(name = "quantity", nullable = false)
//     @Builder.Default
//     private Integer quantity = 1;

//     @Column(name = "price", nullable = false)
//     @NotNull
//     private Double item_price;

//     @OneToMany(mappedBy = "extras", cascade = CascadeType.ALL, orphanRemoval = true)
//     @Builder.Default
//     private List<Cart_items_Extra> cart_item_extra = new ArrayList<>();

//     @OneToMany(mappedBy = "extras", cascade = CascadeType.ALL, orphanRemoval = true)
//     @Builder.Default
//     private List<Order_item_extra> order_item_extras = new ArrayList<>();

    // @PrePersist
    // void assignValues() {
    //     cart_item_extra = new ArrayList<>();
    //     order_item_extras = new ArrayList<>();
    // }
// }

