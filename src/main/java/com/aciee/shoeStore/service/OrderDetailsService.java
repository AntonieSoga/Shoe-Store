package com.aciee.shoeStore.service;

import com.aciee.shoeStore.model.OrderDetailsEntity;
import com.aciee.shoeStore.repository.OrderDetailsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailsService {

    private final OrderDetailsRepository orderDetailsRepository;

    public OrderDetailsService(OrderDetailsRepository orderDetailsRepository) {
        this.orderDetailsRepository = orderDetailsRepository;
    }

    public List<OrderDetailsEntity> getAllOrderDetails() {
        return orderDetailsRepository.findAll();
    }

    public Optional<OrderDetailsEntity> getOrderDetailById(Long orderDetailId) {
        return orderDetailsRepository.findById(orderDetailId);
    }

    public void createOrderDetail(OrderDetailsEntity orderDetail) {
        orderDetailsRepository.save(orderDetail);
    }

    public OrderDetailsEntity updateOrderDetail(Long orderDetailId, OrderDetailsEntity updatedOrderDetail) {
        if (orderDetailsRepository.existsById(orderDetailId)) {
            updatedOrderDetail.setOrderDetailId(orderDetailId);
            return orderDetailsRepository.save(updatedOrderDetail);
        }
        return null; // Handle not found scenario
    }

    public void deleteOrderDetail(Long orderDetailId) {
        orderDetailsRepository.deleteById(orderDetailId);
    }
}
