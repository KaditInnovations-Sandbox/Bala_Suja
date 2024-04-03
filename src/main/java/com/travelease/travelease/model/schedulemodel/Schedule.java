package com.travelease.travelease.model.schedulemodel;

import java.time.LocalDateTime;

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
@Table(name = "Schedule")
public class Schedule {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ScheduleId")
    private Long ScheduleId;

    @Column(name = "ScheduleStartDate")
    private LocalDateTime ScheduleStartDate;

    @Column(name = "ScheduleEndDate")
    private LocalDateTime ScheduleEndDate;

    @Column(name = "ScheduleCreatedAt")
    private LocalDateTime ScheduleCreatedAt=LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "driverId", nullable = false)
    private Driver driverId;

    @ManyToOne
    @JoinColumn(name = "routeId", nullable = false)
    private route routeId;

    
}
