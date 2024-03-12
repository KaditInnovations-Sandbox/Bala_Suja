package com.travelease.travelease.model.hubmodel;
import com.travelease.travelease.model.schedulemodel.Trip;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DriverLiveLocation")
public class DriverTripAssociation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DriverLiveLocationId")
    private Long DriverLiveLocationId;
    
    @ManyToOne
    @JoinColumn(name = "driverId", nullable = false)
    private Driver driverId;

    @Column(name = "Longitude")
    private Double Longitude;

    @Column(name = "Latitude")
    private Double Latitude;

    @ManyToOne
    @JoinColumn(name = "TripId", nullable = false)
    private Trip TripId;

}
