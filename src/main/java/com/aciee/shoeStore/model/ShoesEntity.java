package com.aciee.shoeStore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "shoes")
@Data
@NoArgsConstructor
public class ShoesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shoe_id")
    private Long shoeId;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private Float size;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "shoeId", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<CartEntity> carts;

    @OneToMany(mappedBy = "shoeId", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<OrderDetailsEntity> orderDetails;

}
