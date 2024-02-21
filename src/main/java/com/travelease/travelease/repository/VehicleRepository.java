package com.travelease.travelease.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travelease.travelease.model.hubmodel.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle,Long> {
    
    @Query(nativeQuery = true, value = "SELECT * FROM Vehicle WHERE vehiclenumber=:vehicleNumber")
    Vehicle findByVehicleNumber(@Param("vehicleNumber") String vehicleNumber);

    @Query(nativeQuery = true, value = "SELECT * FROM Vehicle e WHERE e.vehicleaccess = true")
    List<Vehicle> findByAccessTrue();

    @Query(nativeQuery = true, value = "SELECT * FROM Vehicle e WHERE e.vehicleaccess = false")
    List<Vehicle> findByAccessFalse();

    @Query(nativeQuery = true, value = "SELECT * FROM Vehicle WHERE vehicleid=:vehicleId")
    Vehicle findByVehicleId(@Param("vehicleId") Long vehicleId);
    
}
