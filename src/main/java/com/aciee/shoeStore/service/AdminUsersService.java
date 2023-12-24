package com.aciee.shoeStore.service;

import com.aciee.shoeStore.model.AdminusersEntity;
import com.aciee.shoeStore.repository.AdminUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminUsersService {

    @Autowired
    private AdminUsersRepository adminUsersRepository;

    public List<AdminusersEntity> getAllAdminUsers() {
        return adminUsersRepository.findAll();
    }

    public Optional<AdminusersEntity> getAdminUserById(Long adminUserId) {
        return adminUsersRepository.findById(adminUserId);
    }

    public AdminusersEntity createAdminUser(AdminusersEntity adminUser) {
        return adminUsersRepository.save(adminUser);
    }

    public AdminusersEntity updateAdminUser(Long adminUserId, AdminusersEntity updatedAdminUser) {
        if (adminUsersRepository.existsById(adminUserId)) {
            updatedAdminUser.setAdminID(adminUserId);
            return adminUsersRepository.save(updatedAdminUser);
        }
        return null; // Handle not found scenario
    }

    public void deleteAdminUser(Long adminUserId) {
        adminUsersRepository.deleteById(adminUserId);
    }
}

