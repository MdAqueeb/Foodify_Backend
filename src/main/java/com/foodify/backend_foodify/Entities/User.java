package com.foodify.backend_foodify.Entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.UniqueElements;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "profile_picture")
    private String profile_picture;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "Name does not empty or null")
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    @NotBlank(message = "email does not empty or null")
    @UniqueElements(message = "email must be unique")
    @Pattern(regexp="^[A-Za-z][A-Za-z0-9._%+-]*@gmail\\\\.com$", message = "Email is not valid")
    private String email;

    
    @Column(name = "password", nullable = false)
    @NotBlank(message = "password does not empty or null")
    @Size(min=4, message="Mininum 4 character must be present")
    private String password;

    @Column(name = "phone_no")
    @Size(min=10, message="Mininum 4 character must be present")
    private String phone_no;

    @Column(name = "role", nullable = false)
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Role role = Role.customer;

    @Column(name = "created_at", nullable = false)
    @Builder.Default
    private LocalDateTime created_at = LocalDateTime.now();

    enum Role{
            owner, 
            customer
    }


    // payments 
    // useraddress
    @OneToMany(mappedBy="user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User_Address> user_address ;
    // whislist 
    @OneToMany(mappedBy="user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Wishlist> wishlists ;
    // cart
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart> cart;
    // restaurents
    @OneToMany(mappedBy="user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Restaurent> restaurent ;
    // orders

    @PrePersist
    void defaultValues() {
        user_address = new ArrayList<>();
        wishlists = new ArrayList<>();
    }
}


