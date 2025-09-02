package com.haiya.video.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "points_mall_items")
public class PointsMallItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "item_name", nullable = false)
    private String itemName;
    
    @Column(name = "item_description")
    private String itemDescription;
    
    @Column(name = "item_image_url")
    private String itemImageUrl;
    
    @Column(name = "required_points", nullable = false)
    private Integer requiredPoints;
    
    @Column(name = "original_price")
    private Double originalPrice; // 原价
    
    @Column(name = "stock_quantity")
    private Integer stockQuantity = 0; // 库存数量
    
    @Column(name = "sold_quantity")
    private Integer soldQuantity = 0; // 已售数量
    
    @Column(name = "item_type")
    private String itemType; // 商品类型：COUPON(优惠券), PHYSICAL(实物), VIRTUAL(虚拟物品)
    
    @Column(name = "is_active")
    private Boolean isActive = true; // 是否上架
    
    @Column(name = "priority")
    private Integer priority = 0; // 排序优先级
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.isActive == null) this.isActive = true;
        if (this.priority == null) this.priority = 0;
        if (this.stockQuantity == null) this.stockQuantity = 0;
        if (this.soldQuantity == null) this.soldQuantity = 0;
    }
    
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    // Constructors
    public PointsMallItem() {}
    
    public PointsMallItem(String itemName, String itemDescription, Integer requiredPoints) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.requiredPoints = requiredPoints;
        this.isActive = true;
        this.priority = 0;
        this.stockQuantity = 0;
        this.soldQuantity = 0;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getItemName() {
        return itemName;
    }
    
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    
    public String getItemDescription() {
        return itemDescription;
    }
    
    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }
    
    public String getItemImageUrl() {
        return itemImageUrl;
    }
    
    public void setItemImageUrl(String itemImageUrl) {
        this.itemImageUrl = itemImageUrl;
    }
    
    public Integer getRequiredPoints() {
        return requiredPoints;
    }
    
    public void setRequiredPoints(Integer requiredPoints) {
        this.requiredPoints = requiredPoints;
    }
    
    public Double getOriginalPrice() {
        return originalPrice;
    }
    
    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }
    
    public Integer getStockQuantity() {
        return stockQuantity;
    }
    
    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity != null ? stockQuantity : 0;
    }
    
    public Integer getSoldQuantity() {
        return soldQuantity;
    }
    
    public void setSoldQuantity(Integer soldQuantity) {
        this.soldQuantity = soldQuantity != null ? soldQuantity : 0;
    }
    
    public String getItemType() {
        return itemType;
    }
    
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    
    public Integer getPriority() {
        return priority;
    }
    
    public void setPriority(Integer priority) {
        this.priority = priority;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    /**
     * 检查商品是否有库存
     * @return true表示有库存
     */
    public boolean hasStock() {
        return this.stockQuantity > 0;
    }
    
    /**
     * 减少库存
     * @param quantity 减少的数量
     * @return true表示减少成功
     */
    public boolean decreaseStock(Integer quantity) {
        if (quantity == null || quantity <= 0) {
            return false;
        }
        
        if (this.stockQuantity < quantity) {
            return false; // 库存不足
        }
        
        this.stockQuantity -= quantity;
        this.soldQuantity += quantity;
        return true;
    }
    
    /**
     * 增加库存
     * @param quantity 增加的数量
     */
    public void increaseStock(Integer quantity) {
        if (quantity == null || quantity <= 0) {
            return;
        }
        
        this.stockQuantity += quantity;
    }
}