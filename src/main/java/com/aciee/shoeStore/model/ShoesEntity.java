package com.aciee.shoeStore.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shoes", schema = "public", catalog = "shoeStore")
public class ShoesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ShoeID")
    private Long shoeID;

    @Column(name = "Brand", nullable = false)
    private String brand;

    @Column(name = "Model", nullable = false)
    private String model;

    @Column(name = "Size", nullable = false)
    private Float size;

    @Column(name = "Color", nullable = false)
    private String color;

    @Column(name = "Price", nullable = false)
    private Double price;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;

    @Column(name = "ImageURL")
    private String imageURL;

}
