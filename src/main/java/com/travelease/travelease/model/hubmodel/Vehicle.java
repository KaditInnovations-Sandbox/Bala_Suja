package com.travelease.travelease.model.hubmodel;

import java.time.LocalDateTime;

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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicleId")
    private Long vehicleId;
    @Column(name = "vehicleCapacity",  nullable = false)
    private String vehicleCapacity;
    @Column(name = "vehicleNumber", unique = true)
    private String vehicleNumber;
    @Column (name = "vehicleCreatedAt")  
    private LocalDateTime vehicleCreatedAt = LocalDateTime.now();
    @Column(name = "vehicleIsActive")
    private Boolean VehicleIsActive = true;;
    @Column(name = "vehicleType",  nullable = false)
    private String VehicleType;
    @Column(name = "lastUpdatedTime")
    private LocalDateTime LastUpdatedTime;
    @Column(name = "vehicleDeletedTime")
    private LocalDateTime VehicleDeletedTime;
}
