package com.haiya.user.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUserCreation() {
        User user = new User("testuser", "password123", "13800000000");
        
        assertNotNull(user.getUsername());
        assertNotNull(user.getPassword());
        assertNotNull(user.getPhoneNumber());
        assertNotNull(user.getCreatedAt());
        assertNotNull(user.getUpdatedAt());
        
        assertEquals("testuser", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertEquals("13800000000", user.getPhoneNumber());
    }

    @Test
    void testUserSettersAndGetters() {
        User user = new User();
        
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setPhoneNumber("13800000000");
        user.setAvatarUrl("https://example.com/avatar.jpg");
        
        assertEquals(1L, user.getId());
        assertEquals("testuser", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertEquals("13800000000", user.getPhoneNumber());
        assertEquals("https://example.com/avatar.jpg", user.getAvatarUrl());
    }
    
    @Test
    void testUserPreUpdate() throws InterruptedException {
        User user = new User("testuser", "password123", "13800000000");
        Long originalUpdatedTime = user.getUpdatedAt();
        
        // 确保在 preUpdate 调用前有时间差
        Thread.sleep(1);
        
        // 模拟更新操作
        user.preUpdate();
        
        // 验证更新时间已改变
        assertNotEquals(originalUpdatedTime, user.getUpdatedAt());
    }
}