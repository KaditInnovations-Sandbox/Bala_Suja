package com.travelease.travelease.model.routemodel;

import java.time.LocalDateTime;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonView;
import com.travelease.travelease.model.companymodel.company;
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
@Table(name = "route")

public class route {
    
    public interface PublicView {}

    
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "routeIdGenerator")
    @GenericGenerator(
            name = "routeIdGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "route_sequence"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1"),
                    @Parameter(name = "allocationSize", value = "1")
            }
    )
    @Column(name = "Id")
    @JsonView(PublicView.class)
    @Id
    private Long Id;
    @Column(name = "RouteId", nullable = false, unique = true)
    @JsonView(PublicView.class)
    private String RouteId;
    @Column(name = "RoutePickup", nullable = false)
    @JsonView(PublicView.class)
    private String RoutePickup;
    @Column(name = "RouteDrop", nullable = false)
    @JsonView(PublicView.class)
    private String RouteDrop;
    @Column(name = "RouteStartTime", nullable = false)
    @JsonView(PublicView.class)
    private LocalTime RouteStartTime;
    @Column(name = "RouteEndTime", nullable = false)
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
