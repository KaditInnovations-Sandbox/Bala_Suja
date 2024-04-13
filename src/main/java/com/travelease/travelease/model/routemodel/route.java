package com.travelease.travelease.model.routemodel;

import java.time.LocalDateTime;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonView;
import com.travelease.travelease.model.companymodel.company;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
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
@Table(name = "route")

public class route {
    
    public interface PublicView {}

    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    @JsonView(PublicView.class)
    @Id
    private Long Id;
    @Column(name = "RouteId", nullable = false, unique = true)
    @JsonView(PublicView.class)
    private String RouteId;
    @Column(name = "RoutePickup")
    @JsonView(PublicView.class)
    private String RoutePickup;
    @Column(name = "RouteDrop")
    @JsonView(PublicView.class)
    private String RouteDrop;
    @Column(name = "RouteStartTime")
    @JsonView(PublicView.class)
    private LocalTime RouteStartTime;
    @Column(name = "RouteEndTime")
    @JsonView(PublicView.class)
    private LocalTime RouteEndTime;
    @Column(name = "RouteIsActive")
    @JsonView(PublicView.class)
    private Boolean RouteIsActive=true;
    @Column(name = "RouteCreatedAt")
    @JsonView(PublicView.class)
    private LocalDateTime RouteCreatedAt=LocalDateTime.now();
    @Column(name = "LastUpdatedTime")
    @JsonView(PublicView.class)
    private LocalDateTime LastUpdatedTime;
    @Column(name = "RouteDeletedTime")
    @JsonView(PublicView.class)
    private LocalDateTime RouteDeletedTime;
    @Column(name = "ScheduledStatus")
    @JsonView(PublicView.class)
    private String ScheduledStatus;
    @Column(name = "Remarks")
    @JsonView(PublicView.class)
    private String Remarks;
    
    @ManyToOne
    @JoinColumn(name = "CompanyId", nullable = false)
    @JsonView(PublicView.class)
    private company CompanyId; 
    

    


}
