package com.travelease.travelease.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travelease.travelease.model.hubmodel.DrivervehicleAssociation;

public interface DriverVehicleAssociationRepository extends JpaRepository<DrivervehicleAssociation,Long>{

    @Query(nativeQuery = true, value ="SELECT * from drivervehicle_association e WHERE e.driver_id=:driverId")
    DrivervehicleAssociation findDriverVehicleByDriverId(@Param("driverId")Long driverId);

    @Query(nativeQuery = true, value ="SELECT * from drivervehicle_association e WHERE e.vehicle_id=:vehicleId")
    DrivervehicleAssociation findDriverVehicleByVehicleId(@Param("vehicleId")Long vehicleId);   

    @Query(value = "SELECT d.*, v.*, e.* " +
                "FROM driver d " +
                "JOIN drivervehicle_association e ON d.driver_id = e.driver_id " +
                "JOIN vehicle v ON e.vehicle_id = v.vehicle_id " +
                "WHERE d.driver_type = :driverType AND d.driver_is_active = true", nativeQuery = true)
    List<Map<String, Object>> findActiveDriversByType(@Param("driverType") String driverType);


    @Query(value = "SELECT d.driver_id,d.driver_created_at,d.driver_deleted_time,d.driver_email,d.driver_is_active,d.driver_last_login,d.driver_name,d.driver_phone,d.driver_type,d.last_updated_time,d.login_time,d.remarks,d.driver_last_login, v.*, e.* " +
                "FROM driver d " +
                "JOIN drivervehicle_association e ON d.driver_id = e.driver_id " +
                "JOIN vehicle v ON e.vehicle_id = v.vehicle_id " +
                "WHERE d.driver_type = :driverType", nativeQuery = true)
    List<Map<String, Object>> findDriversByType(@Param("driverType") String driverType);



    
}
