package com.travelease.travelease.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travelease.travelease.model.passengermodel.passenger;
import com.travelease.travelease.model.companymodel.company;


public interface PassengerRepository extends JpaRepository<passenger,Long>{
    @Query(nativeQuery = true, value ="SELECT * from Passenger e WHERE e.passengerphone=:PassengerPhone")
    passenger findByPassengerPhone(@Param("PassengerPhone")BigInteger PassengerPhone);

    @Query(nativeQuery = true, value = "SELECT * FROM passenger e WHERE e.passenger_is_active = true")
    List<passenger> findByAccessTrue();

    @Query(nativeQuery = true, value = "SELECT * FROM passenger e WHERE e.passenger_is_active = false")
    List<passenger> findByAccessFalse();

    @Query(nativeQuery = true, value ="SELECT * from passenger e WHERE e.passengerid=:id")
    passenger checkById(@Param("id")Long passengerId);

    @Query(nativeQuery = true, value ="SELECT * from passenger e WHERE e.company_id=:companyid")
    List<passenger> findByCompanyId(@Param("companyid")Long companyId);

    @Query(nativeQuery = true, value = "SELECT * FROM passenger e WHERE e.route_id =:routeid")
    List<passenger> findByRouteId(@Param("routeid") Long routeid);
   
    
}
