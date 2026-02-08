package com.foodify.backend_foodify.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.foodify.backend_foodify.DTO.ForgotPassword;
import com.foodify.backend_foodify.DTO.LoginData;
import com.foodify.backend_foodify.Entities.User;
import com.foodify.backend_foodify.Exceptions.InvalidPasswordException;
import com.foodify.backend_foodify.Exceptions.PasswordMismatchException;
import com.foodify.backend_foodify.Exceptions.ResourceNotFoundException;
import com.foodify.backend_foodify.Repository.UserRepo;

@Service
public class UserService {

    @Autowired
    private UserRepo usrRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usrRepo.save(user);
    }

    public User validateCredentials(LoginData user) {
        User usr = verifyEmail(user.getEmail());
        
        if(!passwordEncoder.matches(user.getPassword(), usr.getPassword())){
            throw new InvalidPasswordException("User Password Invalid");
        }
        return usr;
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
    
}
