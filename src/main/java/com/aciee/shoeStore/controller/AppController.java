package com.aciee.shoeStore.controller;

import com.aciee.shoeStore.model.ShoesEntity;
import com.aciee.shoeStore.model.UsersEntity;
import com.aciee.shoeStore.service.ShoesService;
import com.aciee.shoeStore.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
//@CrossOrigin(origins = "http://localhost:8080")
public class AppController {

    private final UsersService usersService;


    private final ShoesService shoesService;


    public AppController(UsersService usersService, ShoesService shoesService) {
        this.usersService = usersService;
        this.shoesService = shoesService;

    }

    @GetMapping("/users")
    public List<UsersEntity> getAllUsers() {
        return usersService.getAllUsers();
    }

    @GetMapping("/users/{username}")
    public UsersEntity getUserById(@PathVariable String username) {
        return usersService.getUserById(username).orElse(null);
    }

    @PostMapping("/users")
    public UsersEntity createUser(@RequestBody UsersEntity user) {
        return usersService.createUser(user);
    }

    @PutMapping("/users/{username}")
    public UsersEntity updateUser(@PathVariable String username, @RequestBody UsersEntity updatedUser) {
        return usersService.updateUser(username, updatedUser);
    }

    @DeleteMapping("/users/{username}")
    public void deleteUser(@PathVariable String username) {
        usersService.deleteUser(username);
    }

    @GetMapping("/shoes")
    public List<ShoesEntity> getAllShoes() {
        return shoesService.getAllShoes();
    }

    @GetMapping("/shoes/{shoeId}")
    public ShoesEntity getShoeById(@PathVariable Long shoeId) {
        return shoesService.getShoeById(shoeId).orElse(null);
    }

    @PostMapping("/shoes")
    public ShoesEntity createShoe(@RequestBody ShoesEntity shoe) {
        return shoesService.createShoe(shoe);
    }

    @PutMapping("/shoes/{shoeId}")
    public ShoesEntity updateShoe(@PathVariable Long shoeId, @RequestBody ShoesEntity updatedShoe) {
        return shoesService.updateShoe(shoeId, updatedShoe);
    }

    @DeleteMapping("/shoes/{shoeId}")
    public void deleteShoe(@PathVariable Long shoeId) {
        shoesService.deleteShoe(shoeId);
    }
}

