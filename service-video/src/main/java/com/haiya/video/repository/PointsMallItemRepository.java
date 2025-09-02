package com.haiya.video.repository;

import com.haiya.video.entity.PointsMallItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointsMallItemRepository extends JpaRepository<PointsMallItem, Long> {
    List<PointsMallItem> findByIsActiveTrueOrderByPriorityDesc();
    List<PointsMallItem> findByItemTypeOrderByPriorityDesc(String itemType);
}