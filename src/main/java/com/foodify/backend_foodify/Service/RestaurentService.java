package com.foodify.backend_foodify.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodify.backend_foodify.Entities.Restaurent;
import com.foodify.backend_foodify.Entities.Restaurent.Active;
import com.foodify.backend_foodify.Exceptions.ResourceNotFoundException;
import com.foodify.backend_foodify.Exceptions.RestaurentNotActiveException;
import com.foodify.backend_foodify.Repository.RestaurentRepo;

@Service
public class RestaurentService {

    @Autowired
    private RestaurentRepo restaurentRepo;

    public Restaurent findRestaurent(Long restaurentId) {
        Optional<Restaurent> restaurent = restaurentRepo.findById(restaurentId);
        if(!restaurent.isPresent()){
            throw new ResourceNotFoundException("Restaurent Not Found");
        }
        Restaurent rst = restaurent.get();
        if(rst.getIsAvailable().equals(Active.closed)){
            throw new RestaurentNotActiveException("Restaurent is Closed");
        }
        else if(rst.getIsAvailable().equals(Active.pending)){
            throw new RestaurentNotActiveException("Restaurent is Not Open yet");
        }
        return rst;
    }
    
}
