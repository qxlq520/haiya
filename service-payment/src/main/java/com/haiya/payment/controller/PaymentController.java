package com.haiya.payment.controller;

import com.haiya.payment.entity.Payment;
import com.haiya.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    /**
     * 创建支付订单
     */
    @PostMapping
    public Payment createPayment(@RequestParam Long userId, @RequestParam BigDecimal amount) {
        return paymentService.createPayment(userId, amount);
    }

    /**
     * 根据ID获取支付信息
     */
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        Optional<Payment> payment = paymentService.getPaymentById(id);
        if (payment.isPresent()) {
            return ResponseEntity.ok(payment.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 根据订单ID获取支付信息
     */
    @GetMapping("/order/{orderId}")
    public ResponseEntity<Payment> getPaymentByOrderId(@PathVariable String orderId) {
        Optional<Payment> payment = paymentService.getPaymentByOrderId(orderId);
        if (payment.isPresent()) {
            return ResponseEntity.ok(payment.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 处理支付
     */
    @PostMapping("/{orderId}/process")
    public ResponseEntity<Payment> processPayment(@PathVariable String orderId, @RequestParam String paymentMethod) {
        Payment payment = paymentService.processPayment(orderId, paymentMethod);
        if (payment != null) {
            return ResponseEntity.ok(payment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 退款
     */
    @PostMapping("/{orderId}/refund")
    public ResponseEntity<Payment> refundPayment(@PathVariable String orderId) {
        Payment payment = paymentService.refundPayment(orderId);
        if (payment != null) {
            return ResponseEntity.ok(payment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}