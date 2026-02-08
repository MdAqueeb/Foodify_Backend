package com.foodify.backend_foodify.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodify.backend_foodify.DTO.OrderStatusDTO;
import com.foodify.backend_foodify.Entities.Cart_Item;
import com.foodify.backend_foodify.Entities.Cart_Restaurent;
import com.foodify.backend_foodify.Entities.Cart_items_Extra;
import com.foodify.backend_foodify.Entities.Order;
import com.foodify.backend_foodify.Entities.Order_item;
import com.foodify.backend_foodify.Entities.Order_item_extra;
import com.foodify.backend_foodify.Entities.Restaurent;
import com.foodify.backend_foodify.Entities.User;
import com.foodify.backend_foodify.Entities.Order.Cancelled_By;
import com.foodify.backend_foodify.Entities.Order.OrderStatus;
import com.foodify.backend_foodify.Entities.Order.PaymentStatus;
import com.foodify.backend_foodify.Exceptions.ResourceConflictException;
import com.foodify.backend_foodify.Exceptions.ResourceNotFoundException;
import com.foodify.backend_foodify.Repository.CartRestaurentRepo;
// import com.foodify.backend_foodify.Repository.OrderItemRepo;
import com.foodify.backend_foodify.Repository.OrderRepo;
// import com.foodify.backend_foodify.Repository.Order_Item_Extra_Repo;
import com.foodify.backend_foodify.Repository.RestaurentRepo;
import com.foodify.backend_foodify.Repository.UserRepo;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

    @Autowired
    private UserRepo usr_repo;

    @Autowired
    private RestaurentRepo rst_repo;

    @Autowired
    private CartRestaurentRepo crtRstRepo;

    @Autowired
    private OrderRepo order_repo;

    // @Autowired
    // private OrderItemRepo ordr_itm_repo;

    // @Autowired
    // private Order_Item_Extra_Repo ordr_ext_repo;

    @Transactional
    public Order createOrder(Long cartRestaurentId, Order order) {

        User customer = usr_repo.findById(order.getUser().getUser_id())
            .orElseThrow(() -> new ResourceNotFoundException("Customer Not Found"));

        Restaurent restaurant = rst_repo.findById(order.getRestaurent().getRestaurant_id())
            .orElseThrow(() -> new ResourceNotFoundException("Restaurant Not Found"));

        Cart_Restaurent crt_rst = crtRstRepo.findById(cartRestaurentId)
            .orElseThrow(() -> new ResourceNotFoundException("Cart Restaurant Not Found"));

        if (!crt_rst.getCart().getUser().getUser_id().equals(customer.getUser_id())) {
            throw new ResourceConflictException("Cart does not belong to this user");
        }

        if (!crt_rst.getRestaurent().getRestaurant_id()
                .equals(restaurant.getRestaurant_id())) {
            throw new ResourceConflictException("Restaurant mismatch");
        }

        order.setUser(customer);
        order.setRestaurent(restaurant);
        order.setTotal_amount(crt_rst.getTotal_amount());
        order.setOrder_status(Order.OrderStatus.created);
        order.setOrder_payment(PaymentStatus.pending_payment);

        List<Order_item> orderItems = new ArrayList<>();

        for (Cart_Item ci : crt_rst.getCart_Items()) {

            Order_item oi = new Order_item();
            oi.setOrder(order);
            // oi.setFood(ci);
            oi.setCart_Item(ci);
            oi.setQuantity(ci.getQuantity());
            oi.setItem_price(ci.getPrice());

            List<Order_item_extra> orderExtras = new ArrayList<>();

            for (Cart_items_Extra cie : ci.getCart_item_extra()) {

                Order_item_extra oie = new Order_item_extra();
                oie.setOrder_item(oi);
                oie.setFood_extra(cie.getFood_extra());
                oie.setTotal_amount(cie.getPrice());

                orderExtras.add(oie);
            }

            oi.setOrder_item_extra(orderExtras);
            orderItems.add(oi);
        }

        order.setOrderItem(orderItems);
        Order savedOrder = order_repo.save(order);
        crtRstRepo.delete(crt_rst);

        return savedOrder;
    }

    public Order changeStatus(Long orderId, OrderStatusDTO updateStatus) {
        Optional<Order> order = order_repo.findById(orderId);
        if(!order.isPresent()){
            throw new ResourceNotFoundException("Order Not Found");
        }

        User owner = usr_repo.findById(updateStatus.getUserid())
            .orElseThrow(() -> new ResourceNotFoundException("Owner id Not Match"));

        if(!order.get().getRestaurent().getUser().getUser_id().equals(owner)){
            throw new ResourceConflictException("Owner id not match");
        }
        
        Order ord = order.get();

        OrderStatus current = ord.getOrder_status();
        OrderStatus next = updateStatus.getOrderStatus();

        if (!isValidOwnerTransition(current, next)) {
            throw new ResourceConflictException(
                "Invalid status transition from " + current + " to " + next
            );
        }
        if(next.equals(OrderStatus.cancelled)){
            ord.setCancelled_By(Cancelled_By.owner);
        }
        ord.setOrder_status(next);
        return order_repo.save(ord);
    }


    private boolean isValidOwnerTransition(OrderStatus current, OrderStatus next) {
        switch (current) {

            case created:
                return next == OrderStatus.cooking      
                    || next == OrderStatus.cancelled;  

            case cooking:
                return next == OrderStatus.on_the_way; 

            case on_the_way:
                return next == OrderStatus.deliverd;  

            default:
                return false;
        }
    }

}
