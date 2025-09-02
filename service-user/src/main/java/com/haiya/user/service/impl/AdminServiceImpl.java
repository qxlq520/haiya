package com.haiya.user.service.impl;

import com.haiya.user.entity.Admin;
import com.haiya.user.repository.AdminRepository;
import com.haiya.user.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
    
    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public Page<Admin> getAllAdmins(Pageable pageable) {
        return adminRepository.findAll(pageable);
    }
    
    @Override
    public Page<Admin> getAdminsByUsername(String username, Pageable pageable) {
        return adminRepository.findAll(pageable);
    }
    
    @Override
    public Optional<Admin> getAdminById(Long id) {
        return adminRepository.findById(id);
    }
    
    @Override
    public Admin createAdmin(Admin admin) {
        // 密码加密
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return adminRepository.save(admin);
    }
    
    @Override
    public Optional<Admin> updateAdmin(Long id, Admin adminDetails) {
        return adminRepository.findById(id).map(admin -> {
            admin.setUsername(adminDetails.getUsername());
            admin.setRole(adminDetails.getRole());
            admin.setEmail(adminDetails.getEmail());
            admin.setPhoneNumber(adminDetails.getPhoneNumber());
            admin.setStatus(adminDetails.getStatus());
            return adminRepository.save(admin);
        });
    }
    
    @Override
    public void deleteAdmin(Long id) {
        adminRepository.deleteById(id);
    }
    
    @Override
    public Optional<Admin> updateAdminStatus(Long id, String status) {
        return adminRepository.findById(id).map(admin -> {
            admin.setStatus(status);
            return adminRepository.save(admin);
        });
    }
    
    @Override
    public boolean existsByUsername(String username) {
        return adminRepository.existsByUsername(username);
    }
    
    @Override
    public boolean existsByEmail(String email) {
        return adminRepository.existsByEmail(email);
    }
    
    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        return adminRepository.existsByPhoneNumber(phoneNumber);
    }
}