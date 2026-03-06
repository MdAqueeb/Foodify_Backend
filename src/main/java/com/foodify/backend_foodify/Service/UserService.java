package com.foodify.backend_foodify.Service;

<<<<<<< HEAD
import java.util.Optional;

=======
>>>>>>> 65c71674c8ce67ebd0e9526d53179f9f756b6a87
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.foodify.backend_foodify.DTO.ForgotPassword;
import com.foodify.backend_foodify.DTO.LoginData;
import com.foodify.backend_foodify.Entities.User;
import com.foodify.backend_foodify.Exceptions.InvalidPasswordException;
import com.foodify.backend_foodify.Exceptions.PasswordMismatchException;
<<<<<<< HEAD
import com.foodify.backend_foodify.Exceptions.ResourceConflictException;
=======
>>>>>>> 65c71674c8ce67ebd0e9526d53179f9f756b6a87
import com.foodify.backend_foodify.Exceptions.ResourceNotFoundException;
import com.foodify.backend_foodify.Repository.UserRepo;

@Service
public class UserService {

    @Autowired
    private UserRepo usrRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createNewUser(User user) {
<<<<<<< HEAD
        User usr = usrRepo.findByEmail(user.getEmail());
        if(usr != null){
            throw new ResourceConflictException("User Already found");
        }
=======
>>>>>>> 65c71674c8ce67ebd0e9526d53179f9f756b6a87
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
<<<<<<< HEAD

    public User getUserById(Long userid) {
        Optional<User> usr = usrRepo.findById(userid);
        if(!usr.isPresent()){
            throw new ResourceNotFoundException("User Not Found");
        }
        return usr.get();
    }
=======
>>>>>>> 65c71674c8ce67ebd0e9526d53179f9f756b6a87
    
}
