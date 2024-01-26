package com.aciee.shoeStore.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;
import lombok.Data;

@Entity
@Table(name = "orders")
@Data
public class OrdersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "order_date")
    private Timestamp orderDate;

    @ManyToOne
    @JoinColumn(name = "username")
    private UsersEntity username;

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @OneToMany(mappedBy = "orderId")
    private Set<OrderDetailsEntity> orderDetails;

    public OrdersEntity() {
        this.orderDate = new Timestamp(System.currentTimeMillis());
    }

    public OrdersEntity(UsersEntity username, double totalAmount) {
        this.username=username;
        this.totalAmount=totalAmount;
        this.orderDate = new Timestamp(System.currentTimeMillis());
    }
}
