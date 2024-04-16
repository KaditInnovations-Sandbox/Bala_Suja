package com.travelease.travelease.model.schedulemodel;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.travelease.travelease.model.hubmodel.Driver;
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
@Table(name = "Schedule")
public class Schedule {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "scheduleIdGenerator")
    @GenericGenerator(
            name = "scheduleIdGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "schedule_sequence"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1"),
                    @Parameter(name = "allocationSize", value = "1")
            }
    )
    @Column(name = "ScheduleId")
    private Long ScheduleId;

    @Column(name = "ScheduleDate")
    private LocalDate ScheduleDate;

    @Column(name = "LastUpdatedTime")
    private LocalDateTime LastUpdatedTime;

    @Column(name = "ScheduleCreatedAt")
    private LocalDateTime ScheduleCreatedAt=LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "driverId", nullable = false)
    private Driver driverId;

    @ManyToOne
    @JoinColumn(name = "routeId", nullable = false)
    private route routeId;

    @Column(name = "ScheduleIsActive")
    private boolean ScheduleIsActive = true;

    @Column(name = "ScheduleDeletedTime")
    private LocalDateTime ScheduleDeletedTime;

    @Column(name = "remarks")
    private String Remarks;

    
}
