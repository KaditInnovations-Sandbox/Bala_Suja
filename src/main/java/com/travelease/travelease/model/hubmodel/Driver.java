package com.travelease.travelease.model.hubmodel;

import java.math.BigInteger;
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
@Table(name = "Driver")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DriverId")
    private Long DriverId;
    @Column(name = "DriverName")
    private String DriverName;
    @Column(name = "DriverPhonenumber",unique = true, nullable = false )
    private BigInteger DriverPhoneNumber;
    @Column(name = "DriverEmail")
    private String DriverEmail;
    @Column(name = "DriverPassword")
    private String DriverPassword;
    @Column(name = "DriverType")
    private String DriverType;
    @Column(name = "DriverIsActive")
    private Boolean DriverIsActive=true;
    @Column(name = "DriverLastLogin")
    private LocalDateTime DriverLastLogin;
    @Column(name = "DriverCreatedAt")
    private LocalDateTime DriverCreatedAt=LocalDateTime.now();
    @Column(name = "LastUpdatedTime")
    private LocalDateTime LastUpdatedTime;
    @Column(name = "DriverDeletedTime")
    private LocalDateTime DriverDeletedTime;
    
}
