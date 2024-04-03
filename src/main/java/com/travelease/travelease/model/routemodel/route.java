package com.travelease.travelease.model.routemodel;

import java.time.LocalDateTime;
import java.time.LocalTime;

import com.travelease.travelease.model.companymodel.company;

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
@Table(name = "route")
public class route {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long Id;
    @Column(name = "RouteId", nullable = false, unique = true)
    private String RouteId;
    @Column(name = "RoutePickup")
    private String RoutePickup;
    @Column(name = "RouteDrop")
    private String RouteDrop;
    @Column(name = "RouteStartTime")
    private LocalTime RouteStartTime;
    @Column(name = "RouteEndTime")
    private LocalTime RouteEndTime;
    @Column(name = "RouteIsActive")
    private Boolean RouteIsActive=true;
    @Column(name = "RouteCreatedAt")
    private LocalDateTime RouteCreatedAt=LocalDateTime.now();
    @Column(name = "LastUpdatedTime")
    private LocalDateTime LastUpdatedTime;
    @Column(name = "RouteDeletedTime")
    private LocalDateTime RouteDeletedTime;
    @Column(name = "ScheduledStatus")
    private String ScheduledStatus;
    @Column(name = "Remarks")
    private String Remarks;
    
    @ManyToOne
    @JoinColumn(name = "CompanyId", nullable = false)
    private company CompanyId; 
    

    


}
