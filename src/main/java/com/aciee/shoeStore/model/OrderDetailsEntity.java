package com.aciee.shoeStore.model;

import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "order_details")
@Data
public class OrderDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Long orderDetailId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrdersEntity orderId;

    @ManyToOne
    @JoinColumn(name = "shoe_id")
    private ShoesEntity shoeId;

    @Column(nullable = false)
    private Integer quantity;

    // Add other relationships or methods as needed
}
