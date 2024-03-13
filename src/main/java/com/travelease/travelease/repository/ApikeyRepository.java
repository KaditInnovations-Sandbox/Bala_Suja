package com.travelease.travelease.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travelease.travelease.model.apikeymodel.ApiKey;

public interface ApikeyRepository extends JpaRepository<ApiKey, Long>{
    
}
