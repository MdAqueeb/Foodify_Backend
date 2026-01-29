package com.foodify.backend_foodify.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.foodify.backend_foodify.Entities.Restaurent;
import com.foodify.backend_foodify.Repository.RestaurentRepo;

@Service
public class RestaurentService {

    @Autowired
    private RestaurentRepo restaurent_repo;

    public List<Restaurent> getallRestaurents(int page) {

        PageRequest pageable = PageRequest.of(page, 6);
        Page<Restaurent> restaurents = restaurent_repo.findAll(pageable);
        
       return restaurents.getContent();
    }
    
}
