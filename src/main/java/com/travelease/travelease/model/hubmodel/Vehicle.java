package com.travelease.travelease.model.hubmodel;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
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
    @GeneratedValue
    @Column(name = "vehicleid")
    private Long vehicleId;
    @Column(name = "vehiclecapacity")
    private String vehicleCapacity;
    @Column(name = "vehiclenumber")
    private String vehicleNumber;
    @Column (name = "vehicleregistred")  
    private LocalDateTime vehicleRegistered;
    @Column(name = "Vehicleaccess")
    private Boolean VehicleAccess;
}
