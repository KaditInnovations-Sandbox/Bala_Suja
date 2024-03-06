package com.travelease.travelease.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travelease.travelease.model.hubmodel.Driver;

public interface DriverRepository extends JpaRepository<Driver,Long>{

    @Query(nativeQuery = true, value ="SELECT * from Driver e WHERE e.driver_phonenumber=:DriverPhonenumber")
    Driver findByDriverPhone(@Param("DriverPhonenumber")BigInteger DriverPhonenumber);

}
