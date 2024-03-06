package com.travelease.travelease.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travelease.travelease.model.loginmodel.DriverLogin;

public interface DriverLoginRepository extends JpaRepository<DriverLogin,Long> {
    
    
}
