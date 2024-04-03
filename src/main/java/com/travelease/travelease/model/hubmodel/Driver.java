package com.travelease.travelease.model.hubmodel;

import java.math.BigInteger;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

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
@Table(name = "Driver")
public class Driver {

    public interface PublicView {}
    public interface PrivateView extends PublicView {}

    @Id
    @JsonView(PublicView.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Driver_id")
    private Long DriverId;
    @JsonView(PublicView.class)
    @Column(name = "Driver_name")
    private String DriverName;
    @JsonView(PublicView.class)
    @Column(name = "Driver_phone",unique = true, nullable = false )
    private BigInteger DriverPhone;
    @Column(name = "Driver_email")
    @JsonView(PublicView.class)
    private String DriverEmail;
    @JsonView(PrivateView.class)
    @Column(name = "Driver_password")
    private String DriverPassword;
    @Column(name = "Driver_type")
    @JsonView(PublicView.class)
    private String DriverType;
    @Column(name = "Driver_is_active")
    @JsonView(PublicView.class)
    private Boolean DriverIsActive=true;
    @Column(name = "Driver-last_login")
    @JsonView(PublicView.class)
    private LocalDateTime DriverLastLogin;
    @Column(name = "Driver_created_at")
    @JsonView(PublicView.class)
    private LocalDateTime DriverCreatedAt=LocalDateTime.now();
    @Column(name = "Last_updated_time")
    private LocalDateTime LastUpdatedTime;
    @JsonView(PublicView.class)
    @Column(name = "Driver_deleted_time")
    private LocalDateTime DriverDeletedTime;
    @JsonView(PublicView.class)
    @Column(name = "Remarks")
    private String Remarks;
    @JsonView(PrivateView.class)
    @Column(name = "Token_id")
    private String TokenId;
    @JsonView(PrivateView.class)
    @Column(name = "Login_time")
    private LocalDateTime LoginTime;
    
}
