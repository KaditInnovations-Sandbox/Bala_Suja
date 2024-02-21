package com.travelease.travelease.model.hubmodel;

import java.math.BigInteger;

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
@Table(name = "Driver")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Driverid")
    private Long DriverId;
    @Column(name = "Drivername")
    private String DriverName;
    @Column(name = "Driverphonenumber")
    private BigInteger DriverPhoneNumber;
    @Column(name = "Driveremail")
    private String DriverEmail;
    @Column(name = "Drivertype")
    private String DriverType;
    @Column(name = "Vehiclecapacity")
    private String VehicleCapacity;
    @Column(name = "Vehiclenumber")
    private String VehicleNumber;
    @Column(name = "Driveraccess")
    private Boolean DriverAccess;
    
}
