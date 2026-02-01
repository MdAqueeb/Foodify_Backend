package com.foodify.backend_foodify.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.foodify.backend_foodify.DTO.ApiResponse;
import com.foodify.backend_foodify.Entities.Menu;
import com.foodify.backend_foodify.Service.MenuService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class MenuController {

    // @Autowired
    // private MenuService menu_service;

    // @GetMapping("{restaurentId}/Menu")
    // public ResponseEntity<ApiResponse<Menu>> getMenu(@PathVariable Long restaurentId) {
    //     Menu menu = menu_service.getMenu(restaurentId);

    //     return null;
    // }
    
}
