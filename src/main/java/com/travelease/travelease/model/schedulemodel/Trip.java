package com.travelease.travelease.model.schedulemodel;

import java.time.LocalDateTime;
import java.time.LocalTime;

import com.travelease.travelease.model.hubmodel.Driver;
import com.travelease.travelease.model.routemodel.route;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

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
@Table(name = "Trip")  
public class Trip {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "tripIdGenerator")
    @GenericGenerator(
            name = "tripIdGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "trip_sequence"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1"),
                    @Parameter(name = "allocationSize", value = "1")
            }
    )
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
