package com.haiya.ecommerce.service;

import com.haiya.ecommerce.entity.Order;
import com.haiya.ecommerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public List<Order> getOrdersByUserIdAndStatus(Long userId, String status) {
        return orderRepository.findByUserIdAndStatus(userId, status);
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Order createOrder(Order order) {
        order.setCreatedAt(System.currentTimeMillis());
        order.setUpdatedAt(System.currentTimeMillis());
        return orderRepository.save(order);
    }

    public Optional<Order> updateOrder(Long id, Order orderDetails) {
        return orderRepository.findById(id).map(order -> {
            order.setUserId(orderDetails.getUserId());
            order.setProductId(orderDetails.getProductId());
            order.setQuantity(orderDetails.getQuantity());
            order.setTotalPrice(orderDetails.getTotalPrice());
            order.setStatus(orderDetails.getStatus());
            order.setUpdatedAt(System.currentTimeMillis());
            return orderRepository.save(order);
        });
    }

    public boolean deleteOrder(Long id) {
        return orderRepository.findById(id).map(order -> {
            orderRepository.delete(order);
            return true;
        }).orElse(false);
    }
}