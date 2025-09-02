package com.haiya.user.service;

import com.haiya.user.entity.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    Page<Admin> getAllAdmins(Pageable pageable);
    
    Page<Admin> getAdminsByUsername(String username, Pageable pageable);
    
    Optional<Admin> getAdminById(Long id);
    
    Admin createAdmin(Admin admin);
    
    Optional<Admin> updateAdmin(Long id, Admin adminDetails);
    
    void deleteAdmin(Long id);
    
    Optional<Admin> updateAdminStatus(Long id, String status);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
    
    boolean existsByPhoneNumber(String phoneNumber);
}