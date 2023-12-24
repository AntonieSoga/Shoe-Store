package com.aciee.shoeStore.service;

import com.aciee.shoeStore.model.OrderdetailsEntity;
import com.aciee.shoeStore.repository.OrderDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailsService {

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    public List<OrderdetailsEntity> getAllOrderDetails() {
        return orderDetailsRepository.findAll();
    }

    public Optional<OrderdetailsEntity> getOrderDetailById(Long orderDetailId) {
        return orderDetailsRepository.findById(orderDetailId);
    }

    public OrderdetailsEntity createOrderDetail(OrderdetailsEntity orderDetail) {
        return orderDetailsRepository.save(orderDetail);
    }

    public OrderdetailsEntity updateOrderDetail(Long orderDetailId, OrderdetailsEntity updatedOrderDetail) {
        if (orderDetailsRepository.existsById(orderDetailId)) {
            updatedOrderDetail.setOrderDetailID(orderDetailId);
            return orderDetailsRepository.save(updatedOrderDetail);
        }
        return null; // Handle not found scenario
    }

    public void deleteOrderDetail(Long orderDetailId) {
        orderDetailsRepository.deleteById(orderDetailId);
    }
}
