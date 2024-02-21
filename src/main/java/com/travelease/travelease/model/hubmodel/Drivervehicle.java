package com.travelease.travelease.model.hubmodel;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
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
@Table(name = "Drivervehicleassociation")
public class Drivervehicle {
    @Id
    @GeneratedValue
    @Column(name = "drivervehicleid")
    private Long drivervechicleid;

    @ManyToOne
    @JoinColumn(name = "driverid", nullable = false)
    private Driver driver;

    @ManyToOne
    @JoinColumn(name = "vehicleid", nullable = false)
    private Vehicle vehicle;
    
}
