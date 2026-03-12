package com.foodify.backend_foodify.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_addresses")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_address_id")
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
    private Integer pin_code;

    @Column(name = "country", nullable = false)
    @Size(min = 4, message = "Must be 4 digit characters")
    private String country;

    @Column(name = "address_picture")
    private String address_picture;

}