package com.travelease.travelease.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travelease.travelease.model.loginmodel.AdminLogin;

public interface AdminLoginRepository extends JpaRepository<AdminLogin,Long>{

   
    @Query(nativeQuery = true, value ="SELECT * from Adminlogin e WHERE e.admin_email=:AdminEmail")
    AdminLogin findByAdminEmail(@Param("AdminEmail")String AdminEmail);

    @Query(nativeQuery = true, value ="SELECT * from Adminlogin e WHERE e.admin_phone=:AdminPhone")
    AdminLogin findByAdminPhone(@Param("AdminPhone")BigInteger AdminPhone);
}
