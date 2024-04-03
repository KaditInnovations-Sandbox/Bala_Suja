package com.travelease.travelease.model.routemodel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
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
@Table(name = "Stops")
public class stops {
    
    @Id
    @GeneratedValue
    @Column(name = "StopId")
    private Long StopId;
    @Column(name = "StopName")
    private String stopName;
    @Column(name = "StopIsActive")
    private Boolean StopIsActive=true;
    
    @ManyToOne
    @JoinColumn(name = "routeId", nullable = false)
    private route routeId;
}

