package com.foodify.backend_foodify.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.foodify.backend_foodify.DTO.ApiResponse;
import com.foodify.backend_foodify.DTO.ForgotPassword;
import com.foodify.backend_foodify.DTO.LoginData;
import com.foodify.backend_foodify.Entities.User;
import com.foodify.backend_foodify.Service.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@Validated
public class UserController {

    @Autowired
    private UserService usrService;

    // singu up
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<User>> Signup(@RequestBody @Valid User user) {
        User usr = usrService.createNewUser(user);
        ApiResponse<User> response = new ApiResponse<>();
        response.setData(usr);
        response.setMessage("User Register Successfull");
        response.setSuccess(true);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    // login
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<User>> postMethodName(@RequestBody LoginData credentials) {
        User usr = usrService.validateCredentials(credentials);
        ApiResponse<User> response = new ApiResponse<>();
        response.setData(usr);
        response.setMessage("User Register Successfull");
        response.setSuccess(true);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
     
    // forgot password 
    @GetMapping("/verifyEmail")
    public ResponseEntity<ApiResponse<User>> getMethodName(@RequestParam String email) {
        User usr = usrService.verifyEmail(email);
        ApiResponse<User> response = new ApiResponse<>();
        response.setData(usr);
        response.setMessage("Email Verified Successfull");
        response.setSuccess(true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }   

    @PatchMapping("/forgot-password")
    public ResponseEntity<ApiResponse<User>> forgotPassword(@RequestBody ForgotPassword email) {
        User usr = usrService.updatePassword(email);
        ApiResponse<User> response = new ApiResponse<>();
        response.setData(usr);
        response.setMessage("Password Updated Successfull");
        response.setSuccess(true);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    } 
    
}
