package com.travelease.travelease.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travelease.travelease.model.hubmodel.Driver;

public interface DriverRepository extends JpaRepository<Driver,Long>{
    
}
