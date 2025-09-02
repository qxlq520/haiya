package com.haiya.video.service;

import com.haiya.video.entity.PointsMallItem;
import com.haiya.video.entity.PointsExchangeRecord;
import com.haiya.video.entity.UserPoints;
import com.haiya.video.repository.PointsMallItemRepository;
import com.haiya.video.repository.PointsExchangeRecordRepository;
import com.haiya.video.repository.UserPointsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.logging.Level;

@Service
public class PointsMallService {

    private static final Logger logger = Logger.getLogger(PointsMallService.class.getName());

    @Autowired
    private PointsMallItemRepository pointsMallItemRepository;
    
    @Autowired
    private PointsExchangeRecordRepository pointsExchangeRecordRepository;
    
    @Autowired
    private UserPointsRepository userPointsRepository;

    /**
     * 创建积分商城商品
     * @param pointsMallItem 商品信息
     * @return 创建的商品
     */
    @Transactional
    public PointsMallItem createMallItem(PointsMallItem pointsMallItem) {
        try {
            PointsMallItem savedItem = pointsMallItemRepository.save(pointsMallItem);
            logger.info("Created mall item with ID: " + savedItem.getId());
            return savedItem;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error creating mall item: " + pointsMallItem.getItemName(), e);
            throw new RuntimeException("Failed to create mall item", e);
        }
    }

    /**
     * 获取所有上架的商品
     * @return 商品列表
     */
    public List<PointsMallItem> getActiveItems() {
        try {
            List<PointsMallItem> items = pointsMallItemRepository.findByIsActiveTrueOrderByPriorityDesc();
            logger.info("Retrieved " + items.size() + " active mall items");
            return items;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error retrieving active mall items", e);
            throw new RuntimeException("Failed to retrieve active mall items", e);
        }
    }

    /**
     * 根据ID获取商品
     * @param itemId 商品ID
     * @return 商品信息
     */
    public Optional<PointsMallItem> getItemById(Long itemId) {
        try {
            Optional<PointsMallItem> item = pointsMallItemRepository.findById(itemId);
            if (item.isPresent()) {
                logger.info("Retrieved mall item with ID: " + itemId);
            } else {
                logger.info("Mall item not found with ID: " + itemId);
            }
            return item;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error retrieving mall item: " + itemId, e);
            throw new RuntimeException("Failed to retrieve mall item", e);
        }
    }

    /**
     * 更新商品信息
     * @param itemId 商品ID
     * @param updatedItem 更新的商品信息
     * @return 更新后的商品
     */
    @Transactional
    public PointsMallItem updateItem(Long itemId, PointsMallItem updatedItem) {
        try {
            Optional<PointsMallItem> itemOptional = pointsMallItemRepository.findById(itemId);
            if (!itemOptional.isPresent()) {
                throw new RuntimeException("Item not found with ID: " + itemId);
            }
            
            PointsMallItem item = itemOptional.get();
            item.setItemName(updatedItem.getItemName());
            item.setItemDescription(updatedItem.getItemDescription());
            item.setItemImageUrl(updatedItem.getItemImageUrl());
            item.setRequiredPoints(updatedItem.getRequiredPoints());
            item.setOriginalPrice(updatedItem.getOriginalPrice());
            item.setStockQuantity(updatedItem.getStockQuantity());
            item.setItemType(updatedItem.getItemType());
            item.setIsActive(updatedItem.getIsActive());
            item.setPriority(updatedItem.getPriority());
            
            PointsMallItem savedItem = pointsMallItemRepository.save(item);
            logger.info("Updated mall item with ID: " + itemId);
            return savedItem;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating mall item: " + itemId, e);
            throw new RuntimeException("Failed to update mall item", e);
        }
    }

    /**
     * 兑换商品
     * @param userId 用户ID
     * @param itemId 商品ID
     * @param quantity 兑换数量
     * @param recipientName 收件人姓名
     * @param recipientPhone 收件人电话
     * @param recipientAddress 收件人地址
     * @return 兑换记录
     */
    @Transactional
    public PointsExchangeRecord exchangeItem(Long userId, Long itemId, Integer quantity,
                                           String recipientName, String recipientPhone, String recipientAddress) {
        try {
            // 检查商品是否存在
            Optional<PointsMallItem> itemOptional = pointsMallItemRepository.findById(itemId);
            if (!itemOptional.isPresent()) {
                throw new RuntimeException("Item not found with ID: " + itemId);
            }
            
            PointsMallItem item = itemOptional.get();
            
            // 检查商品是否上架
            if (!item.getIsActive()) {
                throw new RuntimeException("Item is not active: " + itemId);
            }
            
            // 检查库存
            if (!item.hasStock() || item.getStockQuantity() < quantity) {
                throw new RuntimeException("Insufficient stock for item: " + itemId);
            }
            
            // 检查用户积分
            Optional<UserPoints> userPointsOptional = userPointsRepository.findByUserId(userId);
            if (!userPointsOptional.isPresent()) {
                throw new RuntimeException("User points record not found for user: " + userId);
            }
            
            UserPoints userPoints = userPointsOptional.get();
            int totalRequiredPoints = item.getRequiredPoints() * quantity;
            
            if (!userPoints.hasEnoughPoints(totalRequiredPoints)) {
                throw new RuntimeException("Insufficient points for user: " + userId);
            }
            
            // 扣除用户积分
            boolean pointsConsumed = userPoints.consumePoints(totalRequiredPoints);
            if (!pointsConsumed) {
                throw new RuntimeException("Failed to consume points for user: " + userId);
            }
            
            // 更新用户积分记录
            userPointsRepository.save(userPoints);
            
            // 减少商品库存
            item.decreaseStock(quantity);
            pointsMallItemRepository.save(item);
            
            // 创建兑换记录
            PointsExchangeRecord exchangeRecord = new PointsExchangeRecord(
                userId, itemId, item.getItemName(), item.getRequiredPoints(), quantity);
            exchangeRecord.setRecipientName(recipientName);
            exchangeRecord.setRecipientPhone(recipientPhone);
            exchangeRecord.setRecipientAddress(recipientAddress);
            
            // 根据商品类型生成相应的兑换信息
            if ("VIRTUAL".equals(item.getItemType())) {
                // 生成优惠券码
                exchangeRecord.setCouponCode(UUID.randomUUID().toString().substring(0, 8).toUpperCase());
            }
            
            PointsExchangeRecord savedRecord = pointsExchangeRecordRepository.save(exchangeRecord);
            
            logger.info("Exchanged item " + itemId + " for user " + userId + ", quantity: " + quantity);
            return savedRecord;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error exchanging item: " + itemId + " for user: " + userId, e);
            throw new RuntimeException("Failed to exchange item", e);
        }
    }

    /**
     * 获取用户的兑换记录
     * @param userId 用户ID
     * @return 兑换记录列表
     */
    public List<PointsExchangeRecord> getUserExchangeRecords(Long userId) {
        try {
            List<PointsExchangeRecord> records = pointsExchangeRecordRepository.findByUserIdOrderByExchangeTimeDesc(userId);
            logger.info("Retrieved " + records.size() + " exchange records for user: " + userId);
            return records;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error retrieving exchange records for user: " + userId, e);
            throw new RuntimeException("Failed to retrieve exchange records", e);
        }
    }

    /**
     * 获取所有兑换记录
     * @return 兑换记录列表
     */
    public List<PointsExchangeRecord> getAllExchangeRecords() {
        try {
            List<PointsExchangeRecord> records = pointsExchangeRecordRepository.findAllByOrderByExchangeTimeDesc();
            logger.info("Retrieved " + records.size() + " exchange records");
            return records;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error retrieving all exchange records", e);
            throw new RuntimeException("Failed to retrieve exchange records", e);
        }
    }

    /**
     * 完成兑换（发货/发放）
     * @param recordId 记录ID
     * @param trackingNumber 快递单号（可选）
     * @return 更新后的记录
     */
    @Transactional
    public PointsExchangeRecord completeExchange(Long recordId, String trackingNumber) {
        try {
            Optional<PointsExchangeRecord> recordOptional = pointsExchangeRecordRepository.findById(recordId);
            if (!recordOptional.isPresent()) {
                throw new RuntimeException("Exchange record not found with ID: " + recordId);
            }
            
            PointsExchangeRecord record = recordOptional.get();
            record.completeExchange();
            
            if (trackingNumber != null && !trackingNumber.isEmpty()) {
                record.setTrackingNumber(trackingNumber);
            }
            
            PointsExchangeRecord savedRecord = pointsExchangeRecordRepository.save(record);
            logger.info("Completed exchange record: " + recordId);
            return savedRecord;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error completing exchange record: " + recordId, e);
            throw new RuntimeException("Failed to complete exchange", e);
        }
    }

    /**
     * 删除商品
     * @param itemId 商品ID
     */
    @Transactional
    public void deleteItem(Long itemId) {
        try {
            pointsMallItemRepository.deleteById(itemId);
            logger.info("Deleted mall item with ID: " + itemId);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting mall item: " + itemId, e);
            throw new RuntimeException("Failed to delete mall item", e);
        }
    }
}