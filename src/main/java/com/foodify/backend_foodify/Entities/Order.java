package com.foodify.backend_foodify.Entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long order_id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @NotNull
    private OrderStatus order_status;

    @Column(name = "cancelled_By")
    // @NotNull
    @Enumerated(EnumType.STRING)
    private Cancelled_By cancelled_By;

    @Column(name = "total_amount", nullable = false)
    @NotNull
    private Double total_amount;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime created_at;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurent_id", nullable = false)
    private Restaurent restaurent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false)
    private User_Address user_address;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_payment", nullable = false)
    private PaymentStatus order_payment;

    public enum OrderStatus{

        // created, cooking, on the way, deliverd,         cancelled
        // confirm, accept, asign to driver, mark pickup, 
        created, 
        cooking, 
        on_the_way, 
        deliverd,
        cancelled
    }

    public enum PaymentStatus{
        pending_payment,
        payment_success,
        payment_failed
    }

    public enum Cancelled_By{
        not_cancel,
        customer,
        owner, 
    }

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order_item> orderItem;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<Payment> payment;

    @PrePersist
    void assign_values(){
        created_at = LocalDateTime.now();
        orderItem = new ArrayList<>();
        payment = new ArrayList<>();
        cancelled_By = Cancelled_By.not_cancel;
        order_status = OrderStatus.created;
        order_payment = PaymentStatus.pending_payment;
    }
}
