package com.haiya.user.repository;

import com.haiya.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByPhoneNumber(String phoneNumber);
    Boolean existsByUsername(String username);
    Boolean existsByPhoneNumber(String phoneNumber);
}