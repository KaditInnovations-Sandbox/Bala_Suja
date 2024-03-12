package com.travelease.travelease.model.loginmodel;
import java.time.LocalDateTime;

import com.travelease.travelease.model.hubmodel.Driver;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DriverLogin")
public class DriverLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long DriverLoginId;

    @OneToOne
    @JoinColumn(name = "Driver",nullable = false)
    private Driver Driver;

    @Column(name = "Tokenid")
    private String TokenId;

    @Column(name = "Timestamp")
    private LocalDateTime Timestamp;
}
