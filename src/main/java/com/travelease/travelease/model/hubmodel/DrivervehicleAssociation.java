package com.travelease.travelease.model.hubmodel;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DrivervehicleAssociation")
public class DrivervehicleAssociation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "driver_vehicle_seq")
    @SequenceGenerator(name = "driver_vehicle_seq", sequenceName = "driver_vehicle_sequence", allocationSize = 1)
    @Column(name = "driverVehicleId")
    private Long driverVechicleId;

    @ManyToOne
    @JoinColumn(name = "driverId", nullable = false)
    private Driver driverId;

    @ManyToOne
    @JoinColumn(name = "vehicleId", nullable = false)
    private Vehicle vehicleId;
    
}
