package com.foodify.backend_foodify.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodify.backend_foodify.Entities.Menu;
import com.foodify.backend_foodify.Entities.Restaurent;
import com.foodify.backend_foodify.Exceptions.ResourceNotFoundException;
import com.foodify.backend_foodify.Repository.MenuRepo;
import com.foodify.backend_foodify.Repository.RestaurentRepo;

@Service
public class MenuService {

    @Autowired
    private MenuRepo menu_repo;

    @Autowired
    private RestaurentRepo restaurentRepo;

    public Menu getMenu(Long restaurentId) {
        Optional<Restaurent> rst = restaurentRepo.findById(restaurentId);
        if(!rst.isPresent()){
            throw new ResourceNotFoundException("Restaurent Not Found");
        }
        Restaurent restaurent = rst.get();
        Menu menu = restaurent.getMenu();
        if(menu == null){
            menu = createMenu(restaurent);
        }
        return menu;
    }

    public Menu createMenu(Restaurent restaurentId){
        Menu menu = new Menu();
        menu.setRestaurent(restaurentId);
        return menu_repo.save(menu);
    }
    
}
