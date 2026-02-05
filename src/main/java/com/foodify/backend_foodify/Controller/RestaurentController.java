package com.foodify.backend_foodify.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.foodify.backend_foodify.DTO.ApiResponse;
import com.foodify.backend_foodify.Entities.Restaurent;
import com.foodify.backend_foodify.Service.RestaurentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/restaurent")
public class RestaurentController {

    @Autowired
    private RestaurentService restaurentService;

    @GetMapping("/{restaurentId}")
    public ResponseEntity<ApiResponse<Restaurent>> getRestaurent(
            @PathVariable Long restaurentId) {

        Restaurent restaurent = restaurentService.findRestaurent(restaurentId);

        ApiResponse<Restaurent> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setMessage("Restaurent fetched successfully");
        response.setData(restaurent);

        return ResponseEntity.ok(response);
    }
}
