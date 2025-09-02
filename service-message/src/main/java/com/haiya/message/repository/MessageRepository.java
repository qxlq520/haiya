package com.haiya.message.repository;

import com.haiya.message.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderIdAndReceiverIdOrderByCreatedAtAsc(Long senderId, Long receiverId);
    List<Message> findByReceiverIdAndStatusOrderByCreatedAtAsc(Long receiverId, String status);
    List<Message> findByReceiverIdAndTypeOrderByCreatedAtDesc(Long receiverId, String type);
}