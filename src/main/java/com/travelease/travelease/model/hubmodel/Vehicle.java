package com.travelease.travelease.model.hubmodel;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Vehicle")
public class Vehicle {

    public interface PublicView {}
    public interface PrivateView extends PublicView {}
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_id")
    private Long vehicleId;
    @Column(name = "vehicle_capacity",  nullable = false)
    private String vehicleCapacity;
    @Column(name = "vehicle_number", unique = true, nullable = false)
    private String vehicleNumber;
    @Column (name = "vehicle_created_at")  
    private LocalDateTime vehicleCreatedAt = LocalDateTime.now();
    @Column(name = "vehicle_is_active")
    private Boolean VehicleIsActive = true;;
    @Column(name = "vehicle_type",  nullable = false)
    @JsonView(PrivateView.class)
    private String VehicleType;
    @Column(name = "last_updated_time")
    private LocalDateTime LastUpdatedTime;
    @Column(name = "vehicle_deleted_time")
    private LocalDateTime VehicleDeletedTime;
    @Column(name = "remarks")
    private String Remarks;
}
