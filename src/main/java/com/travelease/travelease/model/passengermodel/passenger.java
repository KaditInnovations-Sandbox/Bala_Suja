package com.travelease.travelease.model.passengermodel;

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
@Table(name = "Passenger")                  
public class passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passengerid")
    private long PassengerId;
    @Column(name = "passengername")
    private String PassengerName;
    @Column(name = "passengeremail")
    private String PassengerEmail;
    @Column(name = "passengerphone")
    private BigInteger PassengerPhone;
    @Column(name = "passengerLocation")
    private String PassengerLocation;
    @Column(name = "PassengerPassword")
    private String PassengerPassword;
    @Column(name = "PassengerIsActive")
    private Boolean PassengerIsActive;
    @Column(name = "PassengerLastLogin")
    private LocalDateTime PassengerLastLogin;
    @Column(name = "PassengerCreatedAt")
    private LocalDateTime PassengerCreatedAt;
    @Column(name = "LastUpdatedTime")
    private LocalDateTime LastUpdatedTime;
    
}
