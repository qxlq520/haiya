package com.haiya.video.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "points_exchange_records")
public class PointsExchangeRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(name = "item_id", nullable = false)
    private Long itemId;
    
    @Column(name = "item_name", nullable = false)
    private String itemName;
    
    @Column(name = "exchange_points", nullable = false)
    private Integer exchangePoints;
    
    @Column(name = "exchange_quantity", nullable = false)
    private Integer exchangeQuantity = 1;
    
    @Column(name = "total_points", nullable = false)
    private Integer totalPoints;
    
    @Column(name = "exchange_status")
    private String exchangeStatus; // 兑换状态：PENDING(待处理), COMPLETED(已完成), CANCELLED(已取消)
    
    @Column(name = "tracking_number")
    private String trackingNumber; // 快递单号（实物商品）
    
    @Column(name = "coupon_code")
    private String couponCode; // 优惠券码（虚拟商品）
    
    @Column(name = "recipient_name")
    private String recipientName; // 收件人姓名
    
    @Column(name = "recipient_phone")
    private String recipientPhone; // 收件人电话
    
    @Column(name = "recipient_address")
    private String recipientAddress; // 收件人地址
    
    @Column(name = "exchange_time", nullable = false)
    private LocalDateTime exchangeTime;
    
    @Column(name = "completed_time")
    private LocalDateTime completedTime; // 完成时间
    
    @Column(name = "cancelled_time")
    private LocalDateTime cancelledTime; // 取消时间
    
    @PrePersist
    public void prePersist() {
        this.exchangeTime = LocalDateTime.now();
        if (this.exchangeStatus == null) {
            this.exchangeStatus = "PENDING";
        }
    }
    
    // Constructors
    public PointsExchangeRecord() {}
    
    public PointsExchangeRecord(Long userId, Long itemId, String itemName, 
                               Integer exchangePoints, Integer exchangeQuantity) {
        this.userId = userId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.exchangePoints = exchangePoints;
        this.exchangeQuantity = exchangeQuantity != null ? exchangeQuantity : 1;
        this.totalPoints = this.exchangePoints * this.exchangeQuantity;
        this.exchangeStatus = "PENDING";
        this.exchangeTime = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Long getItemId() {
        return itemId;
    }
    
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
    
    public String getItemName() {
        return itemName;
    }
    
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    
    public Integer getExchangePoints() {
        return exchangePoints;
    }
    
    public void setExchangePoints(Integer exchangePoints) {
        this.exchangePoints = exchangePoints;
    }
    
    public Integer getExchangeQuantity() {
        return exchangeQuantity;
    }
    
    public void setExchangeQuantity(Integer exchangeQuantity) {
        this.exchangeQuantity = exchangeQuantity != null ? exchangeQuantity : 1;
        // 同时更新总积分
        if (this.exchangePoints != null) {
            this.totalPoints = this.exchangePoints * this.exchangeQuantity;
        }
    }
    
    public Integer getTotalPoints() {
        return totalPoints;
    }
    
    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }
    
    public String getExchangeStatus() {
        return exchangeStatus;
    }
    
    public void setExchangeStatus(String exchangeStatus) {
        this.exchangeStatus = exchangeStatus;
    }
    
    public String getTrackingNumber() {
        return trackingNumber;
    }
    
    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }
    
    public String getCouponCode() {
        return couponCode;
    }
    
    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }
    
    public String getRecipientName() {
        return recipientName;
    }
    
    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }
    
    public String getRecipientPhone() {
        return recipientPhone;
    }
    
    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }
    
    public String getRecipientAddress() {
        return recipientAddress;
    }
    
    public void setRecipientAddress(String recipientAddress) {
        this.recipientAddress = recipientAddress;
    }
    
    public LocalDateTime getExchangeTime() {
        return exchangeTime;
    }
    
    public void setExchangeTime(LocalDateTime exchangeTime) {
        this.exchangeTime = exchangeTime;
    }
    
    public LocalDateTime getCompletedTime() {
        return completedTime;
    }
    
    public void setCompletedTime(LocalDateTime completedTime) {
        this.completedTime = completedTime;
    }
    
    public LocalDateTime getCancelledTime() {
        return cancelledTime;
    }
    
    public void setCancelledTime(LocalDateTime cancelledTime) {
        this.cancelledTime = cancelledTime;
    }
    
    /**
     * 完成兑换
     */
    public void completeExchange() {
        this.exchangeStatus = "COMPLETED";
        this.completedTime = LocalDateTime.now();
    }
    
    /**
     * 取消兑换
     */
    public void cancelExchange() {
        this.exchangeStatus = "CANCELLED";
        this.cancelledTime = LocalDateTime.now();
    }
}