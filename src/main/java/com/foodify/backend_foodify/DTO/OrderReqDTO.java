package com.foodify.backend_foodify.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderReqDTO {
    @JsonProperty("userId")
    private Long userId;
    
    @JsonProperty("restaurantId")
    private Long restaurantId;
    
    @JsonProperty("address_name")
    private String addressName;
    
    @JsonProperty("area")
    private String area;
    
    @JsonProperty("city")
    private String city;
    
    @JsonProperty("pin_code")
    private Integer pinCode;
    
    @JsonProperty("country")
    private String country;
}
