package com.aciee.shoeStore.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "adminusers", schema = "public", catalog = "shoeStore")
public class AdminusersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AdminID")
    private Long adminID;

    @OneToOne
    @JoinColumn(name = "UserID", unique = true)
    private UsersEntity user;

    @Column(name = "Role", nullable = false)
    private String role;

}
