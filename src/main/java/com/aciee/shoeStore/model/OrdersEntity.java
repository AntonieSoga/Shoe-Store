package com.aciee.shoeStore.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Setter
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders", schema = "public", catalog = "shoeStore")
public class OrdersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderID")
    private Long orderID;

    @Column(name = "OrderDate")
    private LocalDateTime orderDate;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private UsersEntity user;

    @Column(name = "TotalAmount", nullable = false)
    private BigDecimal totalAmount;

}
