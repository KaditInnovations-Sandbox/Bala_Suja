package com.travelease.travelease.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travelease.travelease.model.adminmodel.Admin;

public interface AdminRepository extends JpaRepository<Admin,Long>{
    
    @Query(nativeQuery = true, value = "SELECT * from Admin e WHERE e.admin_id=:id")
    Admin checkById(@Param("id")Long id);
    
    @Query(nativeQuery = true, value = "SELECT case WHEN COUNT(e.admin_id) > 0 THEN 'true' ELSE 'false' END FROM admin e WHERE e.admin_email = :email GROUP BY e.admin_is_active")
    String checkAdminExistence(@Param("email")String email);

    @Query(nativeQuery = true, value ="SELECT * from Admin e WHERE e.admin_email=:AdminEmail")
    Admin findByAdminEmail(@Param("AdminEmail")String AdminEmail);

    @Query(nativeQuery = true, value ="SELECT * from Admin e WHERE e.admin_phone=:AdminPhone")
    Admin findByAdminPhone(@Param("AdminPhone")BigInteger AdminPhone);

    @Query(nativeQuery = true, value ="SELECT admin_id from Admin e WHERE e.admin_email=:AdminEmail")
    Long findByAdminIdEmail(@Param("AdminEmail")String AdminEmail);

    @Query(nativeQuery = true, value = "SELECT * FROM Admin e WHERE e.admin_is_active = true")
    List<Admin> findByAccessTrue();

    @Query(nativeQuery = true, value = "SELECT * FROM Admin e WHERE e.admin_is_active = false")
    List<Admin> findByAccessFalse();

    @Query(nativeQuery = true, value = "SELECT * FROM Admin e WHERE e.admin_role_type =:type ")
    List<Admin> findByType(@Param("type") String type);

    @Query(nativeQuery = true, value = "SELECT * FROM Admin e WHERE e.admin_role_type =:type AND e.admin_is_active = true")
    List<Admin> findActiveAdminByType(@Param("type") String type);

    @Query(nativeQuery = true, value = "SELECT * FROM Admin e WHERE e.admin_role_type =:type AND e.admin_is_active = false")
    List<Admin> findInactiveAdminByType(@Param("type")String type);

    
}
