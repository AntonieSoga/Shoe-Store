package com.aciee.shoeStore.service;

import com.aciee.shoeStore.model.UsersEntity;
import com.aciee.shoeStore.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    public List<UsersEntity> getAllUsers() {
        return usersRepository.findAll();
    }

    public Optional<UsersEntity> getUserById(String username) {
        return usersRepository.findById(username);
    }

    public UsersEntity createUser(UsersEntity user) {
        return usersRepository.save(user);
    }

    public UsersEntity updateUser(String username, UsersEntity updatedUser) {
        if (usersRepository.existsById(username)) {
            updatedUser.setUsername(username);
            return usersRepository.save(updatedUser);
        }
        return null; // Handle not found scenario
    }



    public void deleteUser(String username) {
        usersRepository.deleteById(username);
    }

    public UsersEntity getUserByUsername(String username) {
        return usersRepository.findByUsername(username).get();
    }
}
