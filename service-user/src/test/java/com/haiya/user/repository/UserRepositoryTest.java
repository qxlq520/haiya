package com.haiya.user.repository;

import com.haiya.user.entity.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application.yml")
@Disabled
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Rollback
    void testSaveAndFindUser() {
        // 创建用户
        User user = new User("testuser1", "password123", "13800000000");
        User savedUser = userRepository.save(user);
        
        // 验证用户已保存
        assertNotNull(savedUser.getId());
        assertEquals("testuser1", savedUser.getUsername());
        
        // 根据ID查找用户
        Optional<User> foundUser = userRepository.findById(savedUser.getId());
        assertTrue(foundUser.isPresent());
        assertEquals("testuser1", foundUser.get().getUsername());
    }

    @Test
    @Rollback
    void testFindByUsername() {
        // 创建并保存用户
        User user = new User("testuser2", "password123", "13800000000");
        userRepository.save(user);
        
        // 根据用户名查找用户
        Optional<User> foundUser = userRepository.findByUsername("testuser2");
        assertTrue(foundUser.isPresent());
        assertEquals("13800000000", foundUser.get().getPhoneNumber());
    }

    @Test
    @Rollback
    void testExistsByUsername() {
        // 创建并保存用户
        User user = new User("testuser3", "password123", "13800000000");
        userRepository.save(user);
        
        // 检查用户名是否存在
        Boolean exists = userRepository.existsByUsername("testuser3");
        assertTrue(exists);
        
        // 检查不存在的用户名
        Boolean notExists = userRepository.existsByUsername("nonexistent");
        assertFalse(notExists);
    }
}