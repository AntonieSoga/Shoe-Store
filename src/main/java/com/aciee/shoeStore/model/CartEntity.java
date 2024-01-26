package com.aciee.shoeStore.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "cart")
@Data
@NoArgsConstructor
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long cartId;

    @ManyToOne
    @JoinColumn(name = "username")
    private UsersEntity username;

    @ManyToOne
    @JoinColumn(name = "shoe_id")
    private ShoesEntity shoeId;

    @Column(nullable = false)
    private Integer quantity;

    public CartEntity(UsersEntity username, ShoesEntity shoe) {
        this.username=username;
        this.shoeId=shoe;
        this.quantity=1;
    }

    public void incrementQuantity(){
        this.quantity++;
    }
    public void decrementQuantity() {
        this.quantity--;
        if (this.quantity < 0) {
            this.quantity = 0;
        }
    }

}
