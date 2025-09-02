package com.haiya.analytics.repository;

import com.haiya.analytics.entity.UserActivity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserActivityRepository extends MongoRepository<UserActivity, String> {
    List<UserActivity> findByUserIdOrderByTimestampDesc(Long userId);
    List<UserActivity> findByActivityTypeOrderByTimestampDesc(String activityType);
    List<UserActivity> findByUserIdAndActivityTypeOrderByTimestampDesc(Long userId, String activityType);
    
    @Query("{'timestamp': {$gte: ?0, $lte: ?1}}")
    List<UserActivity> findByTimestampBetween(Date start, Date end);
    
    @Query("{'targetId': ?0, 'targetType': ?1}")
    List<UserActivity> findByTargetIdAndTargetType(Long targetId, String targetType);
    
    Long countByActivityTypeAndTimestampBetween(String activityType, Date start, Date end);
    Long countByUserIdAndActivityType(Long userId, String activityType);
}