package com.travelease.travelease.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travelease.travelease.model.passengermodel.passenger;

public interface PassengerRepository extends JpaRepository<passenger,Long>{
    @Query(nativeQuery = true, value ="SELECT * from Passenger e WHERE e.passenger_phone=:PassengerPhone")
    passenger findByPassengerPhone(@Param("PassengerPhone")BigInteger PassengerPhone);

    
}
