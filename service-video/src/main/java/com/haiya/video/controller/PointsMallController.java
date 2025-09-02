package com.haiya.video.controller;

import com.haiya.video.entity.PointsMallItem;
import com.haiya.video.entity.PointsExchangeRecord;
import com.haiya.video.service.PointsMallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/points-mall")
public class PointsMallController {

    @Autowired
    private PointsMallService pointsMallService;

    /**
     * 获取所有上架的商品
     * @return 商品列表
     */
    @GetMapping("/items")
    public ResponseEntity<List<PointsMallItem>> getActiveItems() {
        List<PointsMallItem> items = pointsMallService.getActiveItems();
        return ResponseEntity.ok(items);
    }

    /**
     * 根据ID获取商品
     * @param itemId 商品ID
     * @return 商品信息
     */
    @GetMapping("/items/{itemId}")
    public ResponseEntity<PointsMallItem> getItemById(@PathVariable Long itemId) {
        Optional<PointsMallItem> item = pointsMallService.getItemById(itemId);
        if (item.isPresent()) {
            return ResponseEntity.ok(item.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 创建积分商城商品
     * @param pointsMallItem 商品信息
     * @return 创建的商品
     */
    @PostMapping("/items")
    public ResponseEntity<PointsMallItem> createMallItem(@RequestBody PointsMallItem pointsMallItem) {
        PointsMallItem createdItem = pointsMallService.createMallItem(pointsMallItem);
        return ResponseEntity.ok(createdItem);
    }

    /**
     * 更新商品信息
     * @param itemId 商品ID
     * @param pointsMallItem 更新的商品信息
     * @return 更新后的商品
     */
    @PutMapping("/items/{itemId}")
    public ResponseEntity<PointsMallItem> updateItem(@PathVariable Long itemId, 
                                                    @RequestBody PointsMallItem pointsMallItem) {
        PointsMallItem updatedItem = pointsMallService.updateItem(itemId, pointsMallItem);
        return ResponseEntity.ok(updatedItem);
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
    @PostMapping("/exchange")
    public ResponseEntity<PointsExchangeRecord> exchangeItem(
            @RequestParam Long userId,
            @RequestParam Long itemId,
            @RequestParam Integer quantity,
            @RequestParam String recipientName,
            @RequestParam String recipientPhone,
            @RequestParam String recipientAddress) {
        
        PointsExchangeRecord exchangeRecord = pointsMallService.exchangeItem(
            userId, itemId, quantity, recipientName, recipientPhone, recipientAddress);
        return ResponseEntity.ok(exchangeRecord);
    }

    /**
     * 获取用户的兑换记录
     * @param userId 用户ID
     * @return 兑换记录列表
     */
    @GetMapping("/exchange/user/{userId}")
    public ResponseEntity<List<PointsExchangeRecord>> getUserExchangeRecords(@PathVariable Long userId) {
        List<PointsExchangeRecord> records = pointsMallService.getUserExchangeRecords(userId);
        return ResponseEntity.ok(records);
    }

    /**
     * 获取所有兑换记录
     * @return 兑换记录列表
     */
    @GetMapping("/exchange/all")
    public ResponseEntity<List<PointsExchangeRecord>> getAllExchangeRecords() {
        List<PointsExchangeRecord> records = pointsMallService.getAllExchangeRecords();
        return ResponseEntity.ok(records);
    }

    /**
     * 完成兑换（发货/发放）
     * @param recordId 记录ID
     * @param trackingNumber 快递单号（可选）
     * @return 更新后的记录
     */
    @PutMapping("/exchange/{recordId}/complete")
    public ResponseEntity<PointsExchangeRecord> completeExchange(@PathVariable Long recordId, 
                                                               @RequestParam(required = false) String trackingNumber) {
        PointsExchangeRecord completedRecord = pointsMallService.completeExchange(recordId, trackingNumber);
        return ResponseEntity.ok(completedRecord);
    }

    /**
     * 删除商品
     * @param itemId 商品ID
     * @return 操作结果
     */
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Map<String, String>> deleteItem(@PathVariable Long itemId) {
        pointsMallService.deleteItem(itemId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Item deleted successfully");
        return ResponseEntity.ok(response);
    }
}