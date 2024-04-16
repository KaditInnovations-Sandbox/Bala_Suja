package com.travelease.travelease.model.hubmodel;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Vehicle")
public class Vehicle {

    public interface PublicView {}
    public interface PrivateView extends PublicView {}
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "vehicleIdGenerator")
    @GenericGenerator(
            name = "vehicleIdGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "vehicle_sequence"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1"),
                    @Parameter(name = "allocationSize", value = "1")
            }
    )
    @JsonView(PublicView.class)
    @Column(name = "vehicle_id")
    private Long vehicleId;
    @JsonView(PublicView.class)
    @Column(name = "vehicle_capacity",  nullable = false)
    private String vehicleCapacity;
    @Column(name = "vehicle_number", unique = true, nullable = false)
    @JsonView(PublicView.class)
    private String vehicleNumber;
    @Column (name = "vehicle_created_at")  
    @JsonView(PublicView.class)
    private LocalDateTime vehicleCreatedAt = LocalDateTime.now();
    @Column(name = "vehicle_is_active")
    @JsonView(PublicView.class)
    private Boolean VehicleIsActive = true;;
    @Column(name = "vehicle_type",  nullable = false)
    @JsonView(PrivateView.class)
    private String VehicleType;
    @Column(name = "last_updated_time")
    @JsonView(PublicView.class)
    private LocalDateTime LastUpdatedTime;
    @Column(name = "vehicle_deleted_time")
    @JsonView(PublicView.class)
    private LocalDateTime VehicleDeletedTime;
    @Column(name = "remarks")
    @JsonView(PublicView.class)
    private String Remarks;
}
