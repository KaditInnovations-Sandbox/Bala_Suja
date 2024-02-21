package com.travelease.travelease.model.companymodel;

import com.travelease.travelease.model.routemodel.RouteStopAssociation;

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
@Table(name = "companyrouteassociation")
public class companyrouteassociation {

    @Id
    @GeneratedValue
    @Column(name = "companyrouteid")
    private Long companyRouteId;

    @ManyToOne
    @JoinColumn(name = "companyid", nullable = false)
    private company companyId;

    @ManyToOne
    @JoinColumn(name = "routeid", nullable = false)
    private RouteStopAssociation routeStopAssociationId;


    
}
