package com.aciee.shoeStore.service;

import com.aciee.shoeStore.model.UsersEntity;
import com.aciee.shoeStore.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    public List<UsersEntity> getAllUsers() {
        return usersRepository.findAll();
    }

    public Optional<UsersEntity> getUserById(Long userId) {
        return usersRepository.findById(userId);
    }

    public UsersEntity createUser(UsersEntity user) {
        return usersRepository.save(user);
    }

    public UsersEntity updateUser(Long userId, UsersEntity updatedUser) {
        if (usersRepository.existsById(userId)) {
            updatedUser.setUserID(userId);
            return usersRepository.save(updatedUser);
        }
        return null; // Handle not found scenario
    }

    public void deleteUser(Long userId) {
        usersRepository.deleteById(userId);
    }
}
