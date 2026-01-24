package com.foodify.backend_foodify.Entities;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_addresses")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User_Address {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long address_id;

    @Column(name = "address_name", nullable = false)
    @NotBlank(message = "address can't be empty or null")
    @Size(min = 4, message = "Must be 4 characters")
    private String address_name;

    @Column(name = "area", nullable = false)
    @NotBlank(message = "area can't be empty or null")
    @Size(min = 4, message = "Must be 4 characters")
    private String area;

    @Column(name = "city", nullable = false)
    @NotBlank(message = "city can't be empty or null")
    @Size(min = 4, message = "Must be 4 characters")
    private String city;

    @Column(name = "pin_code", nullable = false)
    @Length(min = 6, max = 6)
    @NotNull
    private Integer pin_code;

    @Column(name = "country", nullable = false)
    @Size(min = 4, message = "Must be 4 digit characters")
    private String country;

    @Column(name = "address_picture")
    private String address_picture;

    @Column(name = "address_picture", nullable = false)
    @Builder.Default
    private boolean Default = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


}
