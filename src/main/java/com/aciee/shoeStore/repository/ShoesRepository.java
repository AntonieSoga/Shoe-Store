package com.aciee.shoeStore.repository;
import com.aciee.shoeStore.model.ShoesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoesRepository extends JpaRepository<ShoesEntity, Long> {
}