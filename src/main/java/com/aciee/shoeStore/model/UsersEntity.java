package com.aciee.shoeStore.model;

import javax.persistence.*;
import java.util.Set;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class UsersEntity {

    @Id
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @OneToMany(mappedBy = "username")
    private Set<AuthoritiesEntity> authorities;

    @OneToMany(mappedBy = "username")
    private Set<CartEntity> carts;

    @OneToMany(mappedBy = "username")
    private Set<OrdersEntity> orders;
}
