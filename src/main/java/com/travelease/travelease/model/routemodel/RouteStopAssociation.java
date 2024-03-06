package com.travelease.travelease.model.routemodel;


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
@Table(name = "Routestopassociation")
public class RouteStopAssociation {
    @Id
    @GeneratedValue
    @Column(name = "routestopid")
    private Long routeStopId;

    @ManyToOne
    @JoinColumn(name = "routeid", nullable = false)
    private route routeid;

    @ManyToOne
    @JoinColumn(name = "stopid", nullable = false)
    private stops stopid;
    
}
