package com.travelease.travelease.model.passengermodel;

import java.math.BigInteger;

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
@Table(name = "passenger")
public class passenger {

    @Id
    @GeneratedValue
    @Column(name = "passengerid")
    private long passengerId;
    @Column(name = "passengername")
    private String passengerName;
    @Column(name = "passengeremail")
    private String passengerEmail;
    @Column(name = "passengerphone")
    private BigInteger passengerPhone;
    @Column(name = "passengerpickup")
    private String passengerPickup;
    
}
