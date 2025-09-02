package com.haiya.payment.service;

import com.haiya.payment.entity.Payment;
import com.haiya.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment createPayment(Long userId, BigDecimal amount) {
        String orderId = "ORDER_" + System.currentTimeMillis() + "_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        Payment payment = new Payment(orderId, userId, amount);
        return paymentRepository.save(payment);
    }

    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    public Optional<Payment> getPaymentByOrderId(String orderId) {
        return paymentRepository.findByOrderId(orderId);
    }

    public Payment processPayment(String orderId, String paymentMethod) {
        Optional<Payment> paymentOpt = paymentRepository.findByOrderId(orderId);
        if (paymentOpt.isPresent()) {
            Payment payment = paymentOpt.get();
            // 模拟支付处理
            if (Math.random() > 0.1) { // 90% 成功率
                payment.setStatus("SUCCESS");
                payment.setPaymentMethod(paymentMethod);
                payment.setTransactionId("TXN_" + System.currentTimeMillis());
            } else {
                payment.setStatus("FAILED");
            }
            payment.setUpdatedAt(new java.util.Date());
            return paymentRepository.save(payment);
        }
        return null;
    }

    public Payment refundPayment(String orderId) {
        Optional<Payment> paymentOpt = paymentRepository.findByOrderId(orderId);
        if (paymentOpt.isPresent()) {
            Payment payment = paymentOpt.get();
            if ("SUCCESS".equals(payment.getStatus())) {
                payment.setStatus("REFUNDED");
                payment.setUpdatedAt(new java.util.Date());
                return paymentRepository.save(payment);
            }
        }
        return null;
    }
}