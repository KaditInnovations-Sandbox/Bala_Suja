package com.travelease.travelease.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travelease.travelease.model.passengermodel.passenger;

public interface PassengerRepository extends JpaRepository<passenger,Long>{
    @Query(nativeQuery = true, value ="SELECT * from Passenger e WHERE e.passengerphone=:PassengerPhone")
    passenger findByPassengerPhone(@Param("PassengerPhone")BigInteger PassengerPhone);

    @Query(nativeQuery = true, value = "SELECT * FROM passenger e WHERE e.passenger_is_active = true")
    List<passenger> findByAccessTrue();

    @Query(nativeQuery = true, value = "SELECT * FROM passenger e WHERE e.passenger_is_active = false")
    List<passenger> findByAccessFalse();

    @Query(nativeQuery = true, value ="SELECT * from passenger e WHERE e.passengerid=:id")
    passenger checkById(@Param("id")Long passengerId);

    
}
