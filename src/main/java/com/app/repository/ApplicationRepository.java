package com.app.repository;

import com.app.entity.Application;
import com.app.entity.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application,Integer> {
    List<Application> findByUserId(Integer userId);
    List<Application> findByUserIdAndStatus(Integer userId, ApplicationStatus status);
    List<Application> findByUserIdAndCompanyContainingIgnoreCase(Integer userId, String company);
}
