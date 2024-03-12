package com.travelease.travelease.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travelease.travelease.model.loginmodel.PassengerLogin;

public interface PassengerLoginRepository extends JpaRepository<PassengerLogin,Long>{
    
}
