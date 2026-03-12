package com.foodify.backend_foodify.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodify.backend_foodify.DTO.AddressDTO;
import com.foodify.backend_foodify.DTO.EarningDTO;
import com.foodify.backend_foodify.DTO.OrderReqDTO;
import com.foodify.backend_foodify.DTO.OrderStatusDTO;
import com.foodify.backend_foodify.Entities.Cart_Item;
import com.foodify.backend_foodify.Entities.Cart_Restaurent;
import com.foodify.backend_foodify.Entities.Cart_items_Extra;
import com.foodify.backend_foodify.Entities.Order;
import com.foodify.backend_foodify.Entities.Order_item;
import com.foodify.backend_foodify.Entities.Order_item_extra;
import com.foodify.backend_foodify.Entities.Restaurent;
import com.foodify.backend_foodify.Entities.User;
import com.foodify.backend_foodify.Entities.OrderAddress;
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
import com.foodify.backend_foodify.Repository.OrderAddressRepo;
import com.foodify.backend_foodify.Repository.UserRepo;
import com.foodify.backend_foodify.DTO.DashboardDTO;
import com.foodify.backend_foodify.DTO.SalesTrendDTO;
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

    @Autowired
    private OrderAddressRepo orderAddressRepo;

    // @Autowired
    // private OrderItemRepo ordr_itm_repo;

    // @Autowired
    // private Order_Item_Extra_Repo ordr_ext_repo;

    @Transactional
    public Order createOrder(Long cartRestaurentId, OrderReqDTO odr) {

        System.out.println("Creating order for userId: " + odr.getUserId() + ", restaurantId: " + odr.getRestaurantId() + ", cartRestaurentId: " + cartRestaurentId);
        User customer = usr_repo.findById(odr.getUserId())
            .orElseThrow(() -> new ResourceNotFoundException("Customer Not Found"));

        Restaurent restaurant = rst_repo.findById(odr.getRestaurantId())
            .orElseThrow(() -> new ResourceNotFoundException("Restaurant Not Found"));

        Cart_Restaurent crt_rst = crtRstRepo.findById(cartRestaurentId)
            .orElseThrow(() -> new ResourceNotFoundException("Cart Restaurant Not Found"));

        System.out.println("Fetched Done ===============>");
        if (!crt_rst.getCart().getUser().getUser_id().equals(customer.getUser_id())) {
            throw new ResourceConflictException("Cart does not belong to this user");
        }

        if (!crt_rst.getRestaurent().getRestaurant_id()
                .equals(restaurant.getRestaurant_id())) {
            throw new ResourceConflictException("Restaurant mismatch");
        }


        // AddressDTO addr = new AddressDTO();
        OrderAddress orderAddress = new OrderAddress();
        orderAddress.setAddress_name(odr.getAddressName());
        orderAddress.setArea(odr.getArea());
        orderAddress.setCity(odr.getCity());
        orderAddress.setPin_code(odr.getPinCode());
        orderAddress.setCountry(odr.getCountry());

        orderAddress = orderAddressRepo.save(orderAddress);

        System.out.println("Order Address saved ===============>");   
        Order order = new Order();

        order.setUser(customer);
        order.setRestaurent(restaurant);
        order.setOrderAddress(orderAddress);
        order.setTotal_amount(crt_rst.getTotal_amount());
        order.setOrder_status(Order.OrderStatus.created);
        order.setOrder_payment(PaymentStatus.pending_payment);

        System.out.println("Order object created ===============>");
        List<Order_item> orderItems = new ArrayList<>();

        for (Cart_Item ci : crt_rst.getCart_Items()) {

            Order_item oi = new Order_item();
            oi.setOrder(order);
            // oi.setFood(ci);
            oi.setFood(ci.getFood());
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
        System.out.println("Order items created ===============>");

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
            .orElseThrow(() -> new ResourceNotFoundException("Owner id Not Found"));

        if(!order.get().getRestaurent().getUser().getUser_id().equals(owner.getUser_id())){
            // System.out.println("Current Restaurent: " + order.get().getRestaurent()+" Owner: " + order.get().getRestaurent().getUser().getUser_id());
            System.out.println(
                "Current Restaurant Owner ID: " + order.get().getRestaurent().getUser().getUser_id()
                + " | Request Owner ID: " + owner.getUser_id()
            );
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

    
    public List<Order> getUserActiveOrders(Long userId) {
        usr_repo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        // exclude delivered status
        return order_repo.findByUserUserIdAndOrderStatusNot(userId, Order.OrderStatus.deliverd.name());
    }

    
    public List<Order> getUserHistoryOrders(Long userId) {
        usr_repo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        List<String> historyStatuses = List.of(Order.OrderStatus.deliverd.name(), Order.OrderStatus.cancelled.name());
        return order_repo.findByUserUserIdAndOrderStatusIn(userId, historyStatuses);
    }

    public List<Order> getUserActiveOrdersByAdmin(Long restaurentId) {
        rst_repo.findById(restaurentId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurent Not Found"));
        // exclude delivered status
        return order_repo.findByRestaurentIdAndOrderStatusNot(restaurentId, Order.OrderStatus.deliverd.name());
    }

    

    public List<Order> getUserHistoryOrdersByAdmin(Long restaurentId) {
        rst_repo.findById(restaurentId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurent Not Found"));
        List<Order.OrderStatus> historyStatuses = List.of(Order.OrderStatus.deliverd, Order.OrderStatus.cancelled);
        return order_repo.findByRestaurentIdAndOrderStatusIn(restaurentId, historyStatuses);
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

    @Transactional
    public EarningDTO getEarningData(Long userId) {
        User owner = usr_repo.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Owner id Not Found"));
        if(owner.getRole() != User.Role.owner){
            throw new ResourceConflictException("User is not an owner");
        }
        List<Restaurent> restaurants = rst_repo.findByUserUserId(userId, "active");
        Double totalEarnings = 0.0;
        Long totalOrders = 0L;
        for(int i = 0;i < restaurants.size();i++){
            List<Order> ord =  order_repo.countByRestaurentUserUserId(restaurants.get(i).getRestaurant_id(), Order.OrderStatus.deliverd.name());
            totalOrders += ord.size();
            for(int j = 0;j < ord.size();j++){
                totalEarnings += ord.get(j).getTotal_amount();
            }
        }
        Double profit = totalEarnings * 0.30;
        return new EarningDTO(totalEarnings, totalOrders, profit);
    }

    @Transactional
    public DashboardDTO getDashboardData(Long userId) {
        User owner = usr_repo.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Owner id Not Found"));
        if(owner.getRole() != User.Role.owner){
            throw new ResourceConflictException("User is not an owner");
        }
        List<Restaurent> restaurants = rst_repo.findByUserUserId(userId, "active");
        
        if(restaurants.size() == 0){
            return new DashboardDTO(0.0, 0L, 0L, 0L, new ArrayList<>());
        }

        List<Long> restaurantIds = restaurants.stream()
            .map(Restaurent::getRestaurant_id)
            .toList();
        Double todaySales = order_repo.getTodaySales(restaurantIds);
    
        Long activeOrders = order_repo.countActiveOrders(restaurantIds);
        Long pendingOrders = order_repo.countPendingOrders(restaurantIds);
        List<SalesTrendDTO> last7DaysSales = order_repo.getLast7DaysSales(restaurantIds);
       
        return new DashboardDTO(todaySales == null? 0.0 : todaySales, activeOrders,(long) restaurants.size(), pendingOrders, last7DaysSales);
    }

}
