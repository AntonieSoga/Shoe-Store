package com.aciee.shoeStore.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
@Data
public class AuthoritiesEntity {

    @Column(name = "username", nullable = false)
    @Id
    private String username;

    @Column(name = "authority", nullable = false)
    private String authority;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username", insertable = false, updatable = false)
    private UsersEntity user;

}
