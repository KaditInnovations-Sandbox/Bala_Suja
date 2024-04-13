package com.travelease.travelease.model.schedulemodel;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.checkerframework.checker.units.qual.C;

import com.travelease.travelease.model.hubmodel.Driver;
import com.travelease.travelease.model.routemodel.route;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Trip")  
public class Trip {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TripId")
    private Long TripId;

    @Column(name = "TripStartTime")
    private LocalTime TripStartTime;

    @Column(name = "TripEndTime")
    private LocalTime TripEndTime;

    @Column(name = "TripDate")
    private LocalDateTime TripDate;

    @Column(name = "Logitude")
    private String Logitude;

    @Column(name = "Latitude")
    private String Latitude;

    @Column(name = "TripStatus")
    private String TripStatus;
        
    @ManyToOne
    @JoinColumn(name = "ScheduleId")
    private Schedule ScheduleId;

    @ManyToOne
    @JoinColumn(name = "driverId")
    private Driver driverId;

    @ManyToOne
    @JoinColumn(name = "vehicleId")
    private route routeId;


}
