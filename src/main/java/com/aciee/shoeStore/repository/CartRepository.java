package com.aciee.shoeStore.repository;

import com.aciee.shoeStore.model.CartEntity;
import com.aciee.shoeStore.model.ShoesEntity;
import com.aciee.shoeStore.model.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {
    CartEntity findByUsernameAndShoeId(UsersEntity userByUsername, ShoesEntity shoe);

    List<CartEntity> findAllByUsername(Optional<UsersEntity> userById);
}