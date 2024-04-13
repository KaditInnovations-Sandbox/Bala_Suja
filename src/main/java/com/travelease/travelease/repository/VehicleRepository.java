package com.travelease.travelease.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travelease.travelease.model.hubmodel.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle,Long> {
    
    @Query(nativeQuery = true, value = "SELECT * FROM Vehicle e WHERE e.vehicle_number =:vehicle_number AND e.vehicle_is_active")
    Vehicle findByVehicleNumber(@Param("vehicle_number") String vehicle_number);

    @Query(nativeQuery = true, value = "SELECT * FROM Vehicle e WHERE e.vehicle_number =:vehicle_number ")
    Vehicle checkByVehicleNumber(@Param("vehicle_number") String vehicle_number);

    @Query(nativeQuery = true, value = "SELECT * FROM Vehicle e WHERE e.vehicle_is_active = true")
    List<Vehicle> findByAccessTrue();

    @Query(nativeQuery = true, value = "SELECT * FROM Vehicle e WHERE e.vehicle_is_active = false")
    List<Vehicle> findByAccessFalse();

    @Query(nativeQuery = true, value = "SELECT * FROM Vehicle WHERE vehicle_id=:vehicleId")
    Vehicle findByVehicleId(@Param("vehicleId") Long vehicleId);

    @Query(nativeQuery = true, value = "SELECT * FROM Vehicle WHERE vehicle_type = :vehicleType")
    List<Vehicle> findByVehicleType(@Param("vehicleType")String vehicleType);

    @Query(nativeQuery = true, value = "SELECT s.vehicle_capacity FROM vehicle s LEFT JOIN trip m ON s.vehicle_id = m.trip_id WHERE m.vehicle_id IS NULL")
    List<String> findAllVehicleCapacity();
    
    @Query(nativeQuery = true, value = "SELECT vehicle_number FROM vehicle WHERE vehicle_capacity=:vehiclecapacity AND vehicle_is_active")
    List<String> vehicleCapacityBasedVehicleNumber(@Param("vehiclecapacity") String vehiclecapacity);
}
