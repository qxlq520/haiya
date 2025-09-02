package com.haiya.user.controller;

import com.haiya.user.entity.User;
import com.haiya.user.service.UserService;
import com.haiya.user.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        // 检查用户名是否已存在
        if (userService.existsByUsername(user.getUsername())) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "用户名已存在");
            return ResponseEntity.badRequest().body(response);
        }

        // 检查手机号是否已存在
        if (userService.existsByPhoneNumber(user.getPhoneNumber())) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "手机号已被注册");
            return ResponseEntity.badRequest().body(response);
        }

        User createdUser = userService.createUser(user);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "用户注册成功");
        response.put("user", createdUser);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "用户名或密码错误");
            return ResponseEntity.badRequest().body(response);
        }

        final String jwt = jwtUtil.generateToken(username);
        Map<String, String> response = new HashMap<>();
        response.put("token", jwt);
        response.put("message", "登录成功");
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(@RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token.substring(7)); // 移除 "Bearer " 前缀
        Optional<User> user = userService.getUserByUsername(username);
        
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("error", "用户不存在");
            return ResponseEntity.badRequest().body(response);
        }
    }
}