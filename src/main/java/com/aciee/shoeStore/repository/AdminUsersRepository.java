package com.aciee.shoeStore.repository;
import com.aciee.shoeStore.model.AdminusersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminUsersRepository extends JpaRepository<AdminusersEntity, Long> {
}