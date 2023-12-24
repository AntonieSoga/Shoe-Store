package com.aciee.shoeStore.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users", schema = "public", catalog = "shoeStore")
public class UsersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private Long userID;

    @Column(name = "Username", nullable = false)
    private String username;

    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "Email", nullable = false)
    private String email;

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "LastName")
    private String lastName;

    @Column(name = "Address")
    private String address;

    @Column(name = "Phone")
    private String phone;
}
