package com.travelease.travelease.model.routemodel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Route")
public class Route {
    
    @Id
    @GeneratedValue
    @Column(name = "routeid")
    private Long routeId;
    @Column(name = "departurepoint")
    private String departurePoint;
    @Column(name = "destinationpoint")
    private String destinationPoint;
    
}
