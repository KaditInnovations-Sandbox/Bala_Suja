package com.travelease.travelease.model.routemodel;

import com.fasterxml.jackson.annotation.JsonView;

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
    
    public interface PublicView {}

    @Id
    @GeneratedValue
    @Column(name = "StopId")
    @JsonView(PublicView.class)
    private Long StopId;
    @Column(name = "StopName")
    @JsonView(PublicView.class)
    private String stopName;
    @Column(name = "StopIsActive")
    @JsonView(PublicView.class)
    private Boolean StopIsActive=true;
    
    @ManyToOne
    @JoinColumn(name = "routeId", nullable = false)
    @JsonView(PublicView.class)
    private route routeId;
}

