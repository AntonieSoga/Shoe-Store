package com.aciee.shoeStore.controller;

import com.aciee.shoeStore.model.*;
import com.aciee.shoeStore.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AppController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private AdminUsersService adminUsersService;

    @Autowired
    private ShoesService shoesService;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private OrderDetailsService orderDetailsService;

    // User Endpoints

    @GetMapping("/users")
    public List<UsersEntity> getAllUsers() {
        return usersService.getAllUsers();
    }

    @GetMapping("/users/{userId}")
    public UsersEntity getUserById(@PathVariable Long userId) {
        return usersService.getUserById(userId).orElse(null);
    }

    @PostMapping("/users")
    public UsersEntity createUser(@RequestBody UsersEntity user) {
        return usersService.createUser(user);
    }

    @PutMapping("/users/{userId}")
    public UsersEntity updateUser(@PathVariable Long userId, @RequestBody UsersEntity updatedUser) {
        return usersService.updateUser(userId, updatedUser);
    }

    @DeleteMapping("/users/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        usersService.deleteUser(userId);
    }

    // AdminUser Endpoints

    @GetMapping("/adminusers")
    public List<AdminusersEntity> getAllAdminUsers() {
        return adminUsersService.getAllAdminUsers();
    }

    @GetMapping("/adminusers/{adminUserId}")
    public AdminusersEntity getAdminUserById(@PathVariable Long adminUserId) {
        return adminUsersService.getAdminUserById(adminUserId).orElse(null);
    }

    @PostMapping("/adminusers")
    public AdminusersEntity createAdminUser(@RequestBody AdminusersEntity adminUser) {
        return adminUsersService.createAdminUser(adminUser);
    }

    @PutMapping("/adminusers/{adminUserId}")
    public AdminusersEntity updateAdminUser(@PathVariable Long adminUserId, @RequestBody AdminusersEntity updatedAdminUser) {
        return adminUsersService.updateAdminUser(adminUserId, updatedAdminUser);
    }

    @DeleteMapping("/adminusers/{adminUserId}")
    public void deleteAdminUser(@PathVariable Long adminUserId) {
        adminUsersService.deleteAdminUser(adminUserId);
    }

    // Shoe Endpoints

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

    // Order Endpoints

    @GetMapping("/orders")
    public List<OrdersEntity> getAllOrders() {
        return ordersService.getAllOrders();
    }

    @GetMapping("/orders/{orderId}")
    public OrdersEntity getOrderById(@PathVariable Long orderId) {
        return ordersService.getOrderById(orderId).orElse(null);
    }

    @PostMapping("/orders")
    public OrdersEntity createOrder(@RequestBody OrdersEntity order) {
        return ordersService.createOrder(order);
    }

    @PutMapping("/orders/{orderId}")
    public OrdersEntity updateOrder(@PathVariable Long orderId, @RequestBody OrdersEntity updatedOrder) {
        return ordersService.updateOrder(orderId, updatedOrder);
    }

    @DeleteMapping("/orders/{orderId}")
    public void deleteOrder(@PathVariable Long orderId) {
        ordersService.deleteOrder(orderId);
    }

    // OrderDetail Endpoints

    @GetMapping("/orderdetails")
    public List<OrderdetailsEntity> getAllOrderDetails() {
        return orderDetailsService.getAllOrderDetails();
    }

    @GetMapping("/orderdetails/{orderDetailId}")
    public OrderdetailsEntity getOrderDetailById(@PathVariable Long orderDetailId) {
        return orderDetailsService.getOrderDetailById(orderDetailId).orElse(null);
    }

    @PostMapping("/orderdetails")
    public OrderdetailsEntity createOrderDetail(@RequestBody OrderdetailsEntity orderDetail) {
        return orderDetailsService.createOrderDetail(orderDetail);
    }

    @PutMapping("/orderdetails/{orderDetailId}")
    public OrderdetailsEntity updateOrderDetail(@PathVariable Long orderDetailId, @RequestBody OrderdetailsEntity updatedOrderDetail) {
        return orderDetailsService.updateOrderDetail(orderDetailId, updatedOrderDetail);
    }

    @DeleteMapping("/orderdetails/{orderDetailId}")
    public void deleteOrderDetail(@PathVariable Long orderDetailId) {
        orderDetailsService.deleteOrderDetail(orderDetailId);
    }
}

