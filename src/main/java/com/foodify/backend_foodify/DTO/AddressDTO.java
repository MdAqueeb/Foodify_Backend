package com.foodify.backend_foodify.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    private String addressName;
    private String area;
    private String city;
    private Integer pinCode;
    private String country;
}