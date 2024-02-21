package com.travelease.travelease.model.routemodel;

import org.springframework.util.RouteMatcher.Route;

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
@Table(name = "stops")
public class stops {
    
    @Id
    @GeneratedValue
    @Column(name = "stopid")
    private Long stopId;
    @Column(name = "stopname")
    private String stopName;
    @Column(name = "location")
    private String location;

}

