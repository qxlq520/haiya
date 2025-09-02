package com.haiya.user.controller;

import com.haiya.user.entity.Admin;
import com.haiya.user.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admins")
public class AdminController {
    
    @Autowired
    private AdminService adminService;
    
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllAdmins(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String status) {
        
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<Admin> adminPage;
        if (username != null && !username.isEmpty()) {
            adminPage = adminService.getAllAdmins(pageable);
        } else {
            adminPage = adminService.getAllAdmins(pageable);
        }
        
        // 过滤状态
        if (status != null && !status.isEmpty()) {
            // 在实际应用中，这里应该在数据库查询层面进行过滤
            // 这里为了简化，仅作演示
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "成功");
        response.put("data", Map.of(
            "list", adminPage.getContent(),
            "total", adminPage.getTotalElements(),
            "page", page,
            "size", size
        ));
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getAdminById(@PathVariable Long id) {
        Optional<Admin> admin = adminService.getAdminById(id);
        Map<String, Object> response = new HashMap<>();
        
        if (admin.isPresent()) {
            response.put("code", 200);
            response.put("message", "成功");
            response.put("data", admin.get());
        } else {
            response.put("code", 404);
            response.put("message", "管理员不存在");
        }
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping
    public ResponseEntity<Map<String, Object>> createAdmin(@RequestBody Admin admin) {
        // 检查用户名是否已存在
        if (adminService.existsByUsername(admin.getUsername())) {
            Map<String, Object> response = new HashMap<>();
            response.put("code", 400);
            response.put("message", "用户名已存在");
            return ResponseEntity.badRequest().body(response);
        }
        
        // 检查邮箱是否已存在
        if (admin.getEmail() != null && adminService.existsByEmail(admin.getEmail())) {
            Map<String, Object> response = new HashMap<>();
            response.put("code", 400);
            response.put("message", "邮箱已存在");
            return ResponseEntity.badRequest().body(response);
        }
        
        // 检查手机号是否已存在
        if (admin.getPhoneNumber() != null && adminService.existsByPhoneNumber(admin.getPhoneNumber())) {
            Map<String, Object> response = new HashMap<>();
            response.put("code", 400);
            response.put("message", "手机号已存在");
            return ResponseEntity.badRequest().body(response);
        }
        
        Admin createdAdmin = adminService.createAdmin(admin);
        
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "创建成功");
        response.put("data", createdAdmin);
        
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateAdmin(@PathVariable Long id, @RequestBody Admin adminDetails) {
        Optional<Admin> updatedAdmin = adminService.updateAdmin(id, adminDetails);
        Map<String, Object> response = new HashMap<>();
        
        if (updatedAdmin.isPresent()) {
            response.put("code", 200);
            response.put("message", "更新成功");
            response.put("data", updatedAdmin.get());
        } else {
            response.put("code", 404);
            response.put("message", "管理员不存在");
        }
        
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteAdmin(@PathVariable Long id) {
        Optional<Admin> admin = adminService.getAdminById(id);
        Map<String, Object> response = new HashMap<>();
        
        if (admin.isPresent()) {
            adminService.deleteAdmin(id);
            response.put("code", 200);
            response.put("message", "删除成功");
        } else {
            response.put("code", 404);
            response.put("message", "管理员不存在");
        }
        
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<Map<String, Object>> updateAdminStatus(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        String status = payload.get("status");
        Optional<Admin> updatedAdmin = adminService.updateAdminStatus(id, status);
        Map<String, Object> response = new HashMap<>();
        
        if (updatedAdmin.isPresent()) {
            response.put("code", 200);
            response.put("message", "状态更新成功");
            response.put("data", updatedAdmin.get());
        } else {
            response.put("code", 404);
            response.put("message", "管理员不存在");
        }
        
        return ResponseEntity.ok(response);
    }
}