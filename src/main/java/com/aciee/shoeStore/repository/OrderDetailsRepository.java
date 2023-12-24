package com.aciee.shoeStore.repository;

import com.aciee.shoeStore.model.OrderdetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderdetailsEntity, Long> {
}