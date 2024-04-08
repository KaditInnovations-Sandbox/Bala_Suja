package com.travelease.travelease.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travelease.travelease.model.loginmodel.AdminLogin;

public interface AdminLoginRepository extends JpaRepository<AdminLogin,Long>{


    @Query(nativeQuery = true, value = "SELECT * from adminlogin e WHERE e.tokenid=:token")
    AdminLogin findByTokenId(@Param("token")String token);
}
