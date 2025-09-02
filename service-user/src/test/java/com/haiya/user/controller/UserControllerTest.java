package com.haiya.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haiya.user.entity.User;
import com.haiya.user.service.UserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(UserController.class)
@Disabled
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateUser() throws Exception {
        // 准备测试数据
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setPhoneNumber("13800000000");
        
        when(userService.createUser(any(User.class))).thenReturn(user);

        // 创建请求体
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("username", "testuser");
        userMap.put("password", "password123");
        userMap.put("phoneNumber", "13800000000");
        String userJson = objectMapper.writeValueAsString(userMap);

        // 执行测试
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetUserById() throws Exception {
        // 准备测试数据
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setPhoneNumber("13800000000");
        
        when(userService.getUserById(1L)).thenReturn(Optional.of(user));

        // 执行测试
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}