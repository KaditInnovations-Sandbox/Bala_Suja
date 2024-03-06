package com.travelease.travelease.model.loginmodel;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.travelease.travelease.model.passengermodel.passenger;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassengerLogin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long PassengerLoginId;

    @OneToOne
    @JoinColumn(name = "Passenger",nullable = false)
    private passenger Passenger;

    @Column(name = "Tokenid")
    private String TokenId;

    @Column(name = "Timestamp")
    private LocalDateTime Timestamp;
    
}
