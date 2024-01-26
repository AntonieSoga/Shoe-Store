package com.aciee.shoeStore.repository;

import com.aciee.shoeStore.model.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, String> {
    Optional<UsersEntity> findByUsername(String username);
}
