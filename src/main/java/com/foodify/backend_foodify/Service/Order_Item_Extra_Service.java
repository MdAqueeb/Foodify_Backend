package com.foodify.backend_foodify.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodify.backend_foodify.Entities.Order_item_extra;
import com.foodify.backend_foodify.Repository.Order_Item_Extra_Repo;

@Service
public class Order_Item_Extra_Service {

    @Autowired private Order_Item_Extra_Repo extraRepo;

    public Order_item_extra addExtra(Order_item_extra extra){
        return extraRepo.save(extra);
    }
}
