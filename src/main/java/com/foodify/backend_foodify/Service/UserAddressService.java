package com.foodify.backend_foodify.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodify.backend_foodify.Entities.User;
import com.foodify.backend_foodify.Entities.User_Address;
import com.foodify.backend_foodify.Exceptions.ResourceConflictException;
import com.foodify.backend_foodify.Exceptions.ResourceNotFoundException;
import com.foodify.backend_foodify.Repository.UserAddressRepo;
import com.foodify.backend_foodify.Repository.UserRepo;

import jakarta.transaction.Transactional;

@Service
public class UserAddressService {
    @Autowired
    private UserAddressRepo addressRepo;
    
    @Autowired
    private UserRepo userRepo;
    
    /**
     * Create a new address for a user
     */
    public User_Address createAddress(Long userId, User_Address address) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        
        address.setUser(user);
        return addressRepo.save(address);
    }
    
    /**
     * Get all addresses for a specific user
     */
    public List<User_Address> getAllAddresses(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        
        return addressRepo.findByUserUserId(userId);
    }
    
    /**
     * Get a specific address by ID
     */
    public User_Address getAddressById(Long addressId, Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        
        User_Address address = addressRepo.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address Not Found"));
        
        if (!address.getUser().getUser_id().equals(userId)) {
            throw new ResourceConflictException("Address does not belong to this user");
        }
        
        return address;
    }
    
    /**
     * Delete a specific address
     */
    @Transactional
    public User_Address deleteAddress(Long addressId, Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        
        User_Address address = addressRepo.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address Not Found"));
        
        if (!address.getUser().getUser_id().equals(userId)) {
            throw new ResourceConflictException("Address does not belong to this user");
        }
        
        addressRepo.deleteById(addressId);
        return address;
    }
    
    /**
     * Modify/Update a specific address
     */
    public User_Address updateAddress(Long addressId, Long userId, User_Address addressDetails) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        
        User_Address address = addressRepo.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address Not Found"));
        
        if (!address.getUser().getUser_id().equals(userId)) {
            throw new ResourceConflictException("Address does not belong to this user");
        }
        
        address.setAddress_name(addressDetails.getAddress_name());
        address.setArea(addressDetails.getArea());
        address.setCity(addressDetails.getCity());
        address.setPin_code(addressDetails.getPin_code());
        address.setCountry(addressDetails.getCountry());
        address.setAddress_picture(addressDetails.getAddress_picture());
        // address.setDefault_address(addressDetails.isDefault_address());
        
        return addressRepo.save(address);
    }
    
    /**
     * Delete all addresses for a user
     */
    @Transactional
    public void deleteAllAddresses(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        
        List<User_Address> addresses = addressRepo.findByUserUserId(userId);
        addressRepo.deleteAll(addresses);
    }
}
