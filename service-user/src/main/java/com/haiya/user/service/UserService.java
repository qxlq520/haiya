package com.haiya.user.service;

import com.haiya.user.entity.User;
import com.haiya.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> updateUser(Long id, User userDetails) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(userDetails.getUsername());
            user.setPhoneNumber(userDetails.getPhoneNumber());
            user.setAvatarUrl(userDetails.getAvatarUrl());
            return userRepository.save(user);
        });
    }

    public boolean deleteUser(Long id) {
        return userRepository.findById(id).map(user -> {
            userRepository.delete(user);
            return true;
        }).orElse(false);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    
    public boolean existsByPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }
    
    /**
     * 验证用户登录
     * @param username 用户名
     * @param rawPassword 原始密码
     * @return 用户对象（如果验证成功）
     */
    public Optional<User> validateUserCredentials(String username, String rawPassword) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() && passwordEncoder.matches(rawPassword, user.get().getPassword())) {
            return user;
        }
        return Optional.empty();
    }
    
    /**
     * 更新用户状态（封禁/解封）
     * @param id 用户ID
     * @param status 状态
     * @return 更新后的用户对象
     */
    public Optional<User> updateUserStatus(Long id, String status) {
        return userRepository.findById(id).map(user -> {
            // 这里可以根据需要添加状态字段到User实体中
            // 例如：user.setStatus(status);
            // 暂时我们只是更新用户信息作为示例
            user.setUpdatedAt(System.currentTimeMillis());
            return userRepository.save(user);
        });
    }
}