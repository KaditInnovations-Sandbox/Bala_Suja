package com.travelease.travelease.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travelease.travelease.exception.DuplicatePhoneNumberException;
import com.travelease.travelease.model.hubmodel.Driver;

public interface DriverRepository extends JpaRepository<Driver,Long>{

    @Query(nativeQuery = true, value ="SELECT * from Driver e WHERE e.driver_phone=:DriverPhonenumber")
    Driver findByDriverPhone(@Param("DriverPhonenumber")BigInteger DriverPhonenumber);

    @Query(nativeQuery = true, value ="SELECT * from Driver e WHERE e.driver_email=:DriverEmail")
    Driver findByDriverEmail(@Param("DriverEmail")String DriverEmail);

    @Query(nativeQuery = true, value = "SELECT * FROM Driver e WHERE e.driver_is_active = true")
    List<Driver> findByAccessTrue();

    @Query(nativeQuery = true, value = "SELECT * FROM Driver e WHERE e.driver_is_active = false")
    List<Driver> findByAccessFalse();

    @Query(nativeQuery = true, value = "SELECT * FROM Driver WHERE driver_type =:driverType")
    List<Driver> findByDriverType(@Param("driverType")String driverType);

    @Query(nativeQuery = true, value = "SELECT * FROM Driver WHERE driver_type =:driverType AND driver_is_active = true")
    List<Driver> findByActiveDriverType(@Param("driverType")String driverType);

    
    @Query(nativeQuery = true, value = "SELECT * FROM Driver WHERE driver_type =:driverType AND driver_is_active = false")
    List<Driver> findByInactiveDriverType(@Param("driverType")String driverType);

    @Query(nativeQuery = true, value ="SELECT * from Driver e WHERE e.driver_id=:id")
    Driver checkById(@Param("id")Long id);


}
