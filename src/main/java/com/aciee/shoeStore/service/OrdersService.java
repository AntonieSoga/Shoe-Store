package com.aciee.shoeStore.service;

import com.aciee.shoeStore.model.OrdersEntity;
import com.aciee.shoeStore.repository.OrdersRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrdersService {

    private final OrdersRepository ordersRepository;

    public OrdersService(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    public List<OrdersEntity> getAllOrders() {
        return ordersRepository.findAll();
    }

    public Optional<OrdersEntity> getOrderById(Long orderId) {
        return ordersRepository.findById(orderId);
    }

    public OrdersEntity createOrder(OrdersEntity order) {
        return ordersRepository.save(order);
    }

    public OrdersEntity updateOrder(Long orderId, OrdersEntity updatedOrder) {
        if (ordersRepository.existsById(orderId)) {
            updatedOrder.setOrderId(orderId);
            return ordersRepository.save(updatedOrder);
        }
        return null; // Handle not found scenario
    }

    public void deleteOrder(Long orderId) {
        ordersRepository.deleteById(orderId);
    }
}
