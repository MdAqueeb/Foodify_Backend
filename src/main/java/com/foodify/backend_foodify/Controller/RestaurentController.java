package com.foodify.backend_foodify.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.foodify.backend_foodify.DTO.ApiResponse;
import com.foodify.backend_foodify.Entities.Restaurent;
import com.foodify.backend_foodify.Service.RestaurentService;

@RestController
@RequestMapping("/restaurent")
public class RestaurentController {

    @Autowired
    private RestaurentService restaurentService;

    @GetMapping("/{restaurentId}")
    public ResponseEntity<ApiResponse<Restaurent>> getRestaurent(
            @PathVariable Long restaurentId) {

        Restaurent restaurent = restaurentService.findRestaurent(restaurentId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, restaurent, "Restaurent fetched successfully")
        );
    }
}
