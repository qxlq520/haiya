package com.haiya.registry.repository;

import com.haiya.registry.entity.Service;
import com.haiya.registry.entity.ServiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    Optional<Service> findByServiceName(String serviceName);
    
    List<Service> findByStatus(ServiceStatus status);
    
    @Query("SELECT s FROM Service s WHERE s.serviceName LIKE %:keyword% OR s.serviceDescription LIKE %:keyword%")
    List<Service> findByKeyword(@Param("keyword") String keyword);
}