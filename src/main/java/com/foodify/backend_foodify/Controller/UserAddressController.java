package com.foodify.backend_foodify.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.foodify.backend_foodify.DTO.ApiResponse;
import com.foodify.backend_foodify.Entities.User_Address;
import com.foodify.backend_foodify.Service.UserAddressService;

import jakarta.validation.Valid;

@RestController
public class UserAddressController {
    @Autowired
    private UserAddressService userAddressService;
    
    @PostMapping("user/{userId}/address")
    public ResponseEntity<ApiResponse<User_Address>> createAddress(@PathVariable Long userId, @RequestBody @Valid User_Address address) {
        User_Address createdAddress = userAddressService.createAddress(userId, address);
        ApiResponse<User_Address> response = new ApiResponse<>();
        response.setData(createdAddress);
        response.setMessage("Address created successfully");
        response.setSuccess(true);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @GetMapping("user/{userId}/addresses")
    public ResponseEntity<ApiResponse<List<User_Address>>> getAllAddresses(@PathVariable Long userId) {
        List<User_Address> addresses = userAddressService.getAllAddresses(userId);
        ApiResponse<List<User_Address>> response = new ApiResponse<>();
        response.setData(addresses);
        response.setMessage("All addresses fetched successfully");
        response.setSuccess(true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("user/{userId}/address/{addressId}")
    public ResponseEntity<ApiResponse<User_Address>> getAddressById(@PathVariable Long userId, @PathVariable Long addressId) {
        User_Address address = userAddressService.getAddressById(addressId, userId);
        ApiResponse<User_Address> response = new ApiResponse<>();
        response.setData(address);
        response.setMessage("Address fetched successfully");
        response.setSuccess(true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PutMapping("user/{userId}/address/{addressId}")
    public ResponseEntity<ApiResponse<User_Address>> updateAddress(@PathVariable Long userId, @PathVariable Long addressId, @RequestBody User_Address addressDetails) {
        User_Address updatedAddress = userAddressService.updateAddress(addressId, userId, addressDetails);
        ApiResponse<User_Address> response = new ApiResponse<>();
        response.setData(updatedAddress);
        response.setMessage("Address updated successfully");
        response.setSuccess(true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @DeleteMapping("user/{userId}/address/{addressId}")
    public ResponseEntity<ApiResponse<User_Address>> deleteAddress(@PathVariable Long userId, @PathVariable Long addressId) {
        User_Address deletedAddress = userAddressService.deleteAddress(addressId, userId);
        ApiResponse<User_Address> response = new ApiResponse<>();
        response.setData(deletedAddress);
        response.setMessage("Address deleted successfully");
        response.setSuccess(true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @DeleteMapping("user/{userId}/addresses")
    public ResponseEntity<ApiResponse<String>> deleteAllAddresses(@PathVariable Long userId) {
        userAddressService.deleteAllAddresses(userId);
        ApiResponse<String> response = new ApiResponse<>();
        response.setData("All addresses cleared");
        response.setMessage("All addresses for user deleted successfully");
        response.setSuccess(true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
