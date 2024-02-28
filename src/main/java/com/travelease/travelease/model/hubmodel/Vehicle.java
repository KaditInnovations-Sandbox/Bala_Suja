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
    @Column(name = "vehicleCapacity")
    private String vehicleCapacity;
    @Column(name = "vehicleNumber")
    private String vehicleNumber;
    @Column (name = "vehicleCreatedAt")  
    private LocalDateTime vehicleCreatedAt;
    @Column(name = "VehicleIsActive")
    private Boolean VehicleAccess;
}
