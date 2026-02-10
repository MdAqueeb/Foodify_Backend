package com.foodify.backend_foodify.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.foodify.backend_foodify.Entities.Menu;
import com.foodify.backend_foodify.Entities.Restaurent;
import com.foodify.backend_foodify.Entities.Restaurent.Status;
import com.foodify.backend_foodify.Entities.User.Role;
import com.foodify.backend_foodify.Entities.User;
import com.foodify.backend_foodify.Exceptions.ResourceConflictException;
import com.foodify.backend_foodify.Exceptions.ResourceNotFoundException;
import com.foodify.backend_foodify.Exceptions.RestaurentNotActiveException;
import com.foodify.backend_foodify.Repository.MenuRepo;
import com.foodify.backend_foodify.Repository.RestaurentRepo;
import com.foodify.backend_foodify.Repository.UserRepo;

import jakarta.transaction.Transactional;

@Service
public class RestaurentService {

    @Autowired
    private RestaurentRepo rst_repo;

    @Autowired
    private UserRepo usr_repo;

    @Autowired
    private MenuRepo menu_repo;

    public Page<Restaurent> getRestaurentsByStatus(String status, int page, int size, String sort) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Restaurent> restaurentPage;
        if((status.equals(Status.active.name())) || (status.equals(Status.closed.name())) || (status.equals(Status.pending.name()) )){
            
            if(sort.equalsIgnoreCase("popular")){
                restaurentPage = rst_repo.findByRestaurentStatusPopular(status, pageable);
            }
            else if(sort.equalsIgnoreCase("recommended")){
                restaurentPage = rst_repo.findByRestaurentStatus(status, pageable);
            }
            else{
                throw new ResourceConflictException("Given sort not found");
            }
            return restaurentPage;
        }
        else{
            if(status.equals("All Status")){
                restaurentPage = rst_repo.findAll(pageable);
                return restaurentPage;
            }
            else if ((status.equalsIgnoreCase(Status.active.name())) || (status.equalsIgnoreCase(Status.closed.name())) || (status.equalsIgnoreCase(Status.pending.name())) ){
                restaurentPage = rst_repo.findByRestaurentStatus(status, pageable);
                return restaurentPage;
            }
            throw new ResourceNotFoundException("Given Status Not Found");
        }
    }

    @Transactional
    public Restaurent createRst(Restaurent restaurent, Long userId) {
        Optional<User> usr = usr_repo.findById(userId);
        if(!usr.isPresent()){
            throw new ResourceNotFoundException("User Not Found");
        }
        else if(usr.get().getRole() == Role.customer){
            throw new ResourceNotFoundException("User Admin Status Not Found");
        }
        restaurent.setUser(usr.get());
        Restaurent rst = rst_repo.save(restaurent);
        Menu menu = new Menu();
        menu.setRestaurent(restaurent);
        menu = menu_repo.save(menu);
        restaurent.setMenu(menu);

        return rst_repo.save(rst);
    }

    public Restaurent findRestaurent(Long restaurentId) {
        Optional<Restaurent> restaurent = rst_repo.findById(restaurentId);
        if(!restaurent.isPresent()){
            throw new ResourceNotFoundException("Restaurent Not Found");
        }
        Restaurent rst = restaurent.get();
        if(rst.getIsAvailable().equals(Status.closed)){
            throw new RestaurentNotActiveException("Restaurent is Closed");
        }
        else if(rst.getIsAvailable().equals(Status.pending)){
            throw new RestaurentNotActiveException("Restaurent is Not Open yet");
        }
        return rst;
    }

    public Restaurent updateRstStatus(Long restaurentId, Long userid, String status) {
        Optional<Restaurent> restaurent = rst_repo.findById(restaurentId);
        if(!restaurent.isPresent()){
            throw new ResourceNotFoundException("Restaurent Not Found");
        }

        Optional<User> usr = usr_repo.findById(userid);
        if(!usr.isPresent()){
            throw new ResourceNotFoundException("User Not Found");
        }

        if(!restaurent.get().getUser().getUser_id().equals(usr.get().getUser_id())){
            throw new ResourceConflictException("Owner Not Match");
        }

        Restaurent rst = restaurent.get();
        if((status.equalsIgnoreCase(Status.active.name())) || (status.equalsIgnoreCase(Status.closed.name())) || (status.equalsIgnoreCase(Status.pending.name()))){
            rst.setIsAvailable(Status.valueOf(status.toLowerCase()));
            rst = rst_repo.save(rst);
        }
        else{
            throw new ResourceConflictException("Status not found");
        }
        return rst;
        
    }

    public Page<Restaurent> getRestaurentsByUser(Long userid, String status, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Restaurent> restaurentPage;

        Optional<User> usr = usr_repo.findById(userid);
        if(!usr.isPresent()){
            throw new ResourceNotFoundException("Owner Not Found");
        }
        else if(usr.get().getRole().equals(Role.customer)){
            throw new ResourceConflictException("Only Owner can update details");
        }
        
        if(status.equals("All Status")){
                restaurentPage = rst_repo.findByUserRest(userid ,pageable);

                return restaurentPage;
            }
        else if ((status.equalsIgnoreCase(Status.active.name())) || (status.equalsIgnoreCase(Status.closed.name())) || (status.equalsIgnoreCase(Status.pending.name())) ){
                restaurentPage = rst_repo.findByUserRstStatus(userid, status, pageable);
                return restaurentPage;
            }
        throw new ResourceNotFoundException("Given Status Not Found");
        
    }

    public Restaurent updateRst(Restaurent restaurent, Long userId, Long restaurentId) {
        Optional<User> usr = usr_repo.findById(userId);
        if(!usr.isPresent()){
            throw new ResourceNotFoundException("Owner Not Found");
        }

        Optional<Restaurent> rst = rst_repo.findById(restaurentId);
        if(!rst.isPresent()){
            throw new ResourceNotFoundException("Restaurent Not Found");
        }

        Restaurent rest = rst.get();
        rest.setRestaurent_picture(restaurent.getRestaurent_picture());
        rest.setRestaurent_service_fee(restaurent.getRestaurent_service_fee());
        rest.setIsAvailable(restaurent.getIsAvailable());
        rest.setIsPopular(restaurent.getIsPopular());
        rest.setRestaurent_address(restaurent.getRestaurent_address());
        rest.setRestaurent_name(restaurent.getRestaurent_name());
        return rst_repo.save(rest);
    }
    
}
