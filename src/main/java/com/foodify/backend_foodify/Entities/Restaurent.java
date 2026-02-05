package com.foodify.backend_foodify.Entities;

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
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "restaurant")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Restaurent {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Long restaurant_id;

    @Column(name = "address")
    private String restaurent_address;

    @Column(name = "brand")
    private String restaurent_brand;

    @Column(name = "picture")
    private String restaurent_picture;

    @Column(name = "active_status", nullable = false)
    // @Builder.Default
    @Enumerated(EnumType.STRING)
    private Active isAvailable;

@Column(name = "delivary_fee")
private Double restaurent_delivary_fee;

@Column(name = "service_fee")
private Double restaurent_service_fee;


    @Column(name = "popular", nullable = false)
    @Builder.Default
    private Boolean isPopular = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "owner_id", nullable = false)
    private User user;

    @OneToOne(mappedBy = "restaurent", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Menu menu;

    @OneToMany(mappedBy = "restaurent", cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JsonIgnore
    private List<Order> order;

@OneToMany(mappedBy = "restaurent", cascade = CascadeType.ALL)
@JsonIgnore
private List<Cart_Restaurent> cart_restaurents;


  @PrePersist
void assignValues() {
    order = new ArrayList<>();
    cart_restaurents = new ArrayList<>();
    isAvailable = Active.active;
}


    public enum Active{
        pending,
        active, 
        closed
    }

    
}
