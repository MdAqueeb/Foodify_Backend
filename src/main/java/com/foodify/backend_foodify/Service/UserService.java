package com.foodify.backend_foodify.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

import com.foodify.backend_foodify.DTO.ForgotPassword;
import com.foodify.backend_foodify.DTO.LoginData;
import com.foodify.backend_foodify.Entities.User;
import com.foodify.backend_foodify.Exceptions.InvalidPasswordException;
import com.foodify.backend_foodify.Exceptions.PasswordMismatchException;
import com.foodify.backend_foodify.Exceptions.ResourceConflictException;
import com.foodify.backend_foodify.Exceptions.ResourceNotFoundException;
import com.foodify.backend_foodify.Repository.UserRepo;
import com.foodify.backend_foodify.DTO.LoginResponse;
import org.springframework.security.core.userdetails.UserDetails;
import com.foodify.backend_foodify.Service.JwtService;
import org.springframework.http.ResponseEntity;
@Service
public class UserService {

    @Autowired
    private UserRepo usrRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;


    public User createNewUser(User user) {
        User usr = usrRepo.findByEmail(user.getEmail());
        if(usr != null){
            throw new ResourceConflictException("User Already found");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usrRepo.save(user);
    }

    public LoginResponse validateCredentials(LoginData user) {
        User usr = verifyEmail(user.getEmail());
        
        if(!passwordEncoder.matches(user.getPassword(), usr.getPassword())){
            throw new InvalidPasswordException("User Password Invalid");
        }
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                usr.getEmail(),
                usr.getPassword(),
                new ArrayList<>()
        );

        String token = jwtService.generateToken(userDetails.getUsername());

        return new LoginResponse(token, usr);
    }

    public User verifyEmail(String email) {
        User usr = usrRepo.findByEmail(email);
        if(usr == null){
            throw new ResourceNotFoundException("User Not Found");
        }
        return usr;

    }

    public User updatePassword(ForgotPassword forgotdata) {
        User usr = verifyEmail(forgotdata.getEmail());
        if(!forgotdata.getPassword().equals(forgotdata.getConfirm_password())){
            throw new PasswordMismatchException("Password & confirm password not match");
        }
        usr.setPassword(passwordEncoder.encode(forgotdata.getPassword()));
        return usrRepo.save(usr);
    }

    public User getUserById(Long userid) {
        Optional<User> usr = usrRepo.findById(userid);
        if(!usr.isPresent()){
            throw new ResourceNotFoundException("User Not Found");
        }
        return usr.get();
    }
    
}
