package com.aciee.shoeStore.service;

import com.aciee.shoeStore.model.ShoesEntity;
import com.aciee.shoeStore.repository.ShoesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoesService {

    @Autowired
    private ShoesRepository shoesRepository;

    public List<ShoesEntity> getAllShoes() {
        return shoesRepository.findAll();
    }

    public Optional<ShoesEntity> getShoeById(Long shoeId) {
        return shoesRepository.findById(shoeId);
    }

    public ShoesEntity createShoe(ShoesEntity shoe) {
        return shoesRepository.save(shoe);
    }

    public ShoesEntity updateShoe(Long shoeId, ShoesEntity updatedShoe) {
        if (shoesRepository.existsById(shoeId)) {
            updatedShoe.setShoeID(shoeId);
            return shoesRepository.save(updatedShoe);
        }
        return null; // Handle not found scenario
    }

    public void deleteShoe(Long shoeId) {
        shoesRepository.deleteById(shoeId);
    }
}
