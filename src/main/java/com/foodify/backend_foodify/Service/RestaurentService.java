package com.foodify.backend_foodify.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.foodify.backend_foodify.Entities.Restaurent;
import com.foodify.backend_foodify.Entities.Restaurent.Status;
import com.foodify.backend_foodify.Entities.User.Role;
import com.foodify.backend_foodify.Entities.User;
import com.foodify.backend_foodify.Exceptions.ResourceNotFoundException;
import com.foodify.backend_foodify.Repository.RestaurentRepo;
import com.foodify.backend_foodify.Repository.UserRepo;

@Service
public class RestaurentService {

    @Autowired
    private RestaurentRepo rst_repo;

    @Autowired
    private UserRepo usr_repo;

    public Page<Restaurent> getRestaurentsByStatus(String status, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        if((status.equals(Status.active.name())) || (status.equals(Status.closed.name())) || (status.equals(Status.pending.name()))){
            Page<Restaurent> restaurentPage = rst_repo.findByRestaurentStatus(status, pageable);
            return restaurentPage;
        }
        else{
            throw new ResourceNotFoundException("Given Status Not Found");
        }
    }

    public Restaurent createRst(Restaurent restaurent, Long userId) {
        Optional<User> usr = usr_repo.findById(userId);
        if(!usr.isPresent()){
            throw new ResourceNotFoundException("User Not Found");
        }
        else if(usr.get().getRole() == Role.customer){
            throw new ResourceNotFoundException("User Admin Status Not Found");
        }
        restaurent.setUser(usr.get());

        return rst_repo.save(restaurent);
    }
    
}
