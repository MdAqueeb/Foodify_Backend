package com.foodify.backend_foodify.Service;

import java.util.Collections;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.foodify.backend_foodify.Entities.User;
import com.foodify.backend_foodify.Repository.UserRepo;
import com.foodify.backend_foodify.Exceptions.ResourceNotFoundException;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email)  {

        User user = userRepo.findByEmail(email);
        if(user == null){
            throw new ResourceNotFoundException("User Not Found with email: " + email);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                new ArrayList<>()
        );
    }
}