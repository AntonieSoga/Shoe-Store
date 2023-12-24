package com.aciee.shoeStore.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orderdetails", schema = "public", catalog = "shoeStore")
public class OrderdetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderDetailID")
    private Long orderDetailID;

    @ManyToOne
    @JoinColumn(name = "OrderID")
    private OrdersEntity order;

    @ManyToOne
    @JoinColumn(name = "ShoeID")
    private ShoesEntity shoe;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;
}
