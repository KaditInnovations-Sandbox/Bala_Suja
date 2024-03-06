package com.travelease.travelease.model.schedulemodel;

import java.time.LocalDateTime;
import java.time.LocalTime;

import com.travelease.travelease.model.routemodel.route;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TripId")
    private Long TripId;

    @Column(name = "TripStartDate")
    private LocalDateTime TripStartDate;

    @Column(name = "TripEndDate")
    private LocalDateTime TripEndDate;

    @Column(name = "TripStartTime")
    private LocalTime TripStartTime;

    @Column(name = "TripEndTime")
    private LocalTime TripEndTime;

    
    @ManyToOne
    @JoinColumn(name = "ScheduleId", nullable = false)
    private Schedule ScheduleId;

    @ManyToOne
    @JoinColumn(name = "routeId", nullable = false)
    private route routeId;
}
