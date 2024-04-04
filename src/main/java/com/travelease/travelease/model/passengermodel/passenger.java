package com.travelease.travelease.model.passengermodel;

import java.math.BigInteger;
import java.time.LocalDateTime;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonView;
import com.travelease.travelease.model.companymodel.company;
import com.travelease.travelease.model.routemodel.route;
import com.travelease.travelease.model.routemodel.stops;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "Passenger")                  
public class passenger {

    public interface PublicView {}
    public interface PrivateView extends PublicView {}

    @Id
    @Column(name = "passengerid")
    @JsonView(PublicView.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long PassengerId;
    @Column(name = "passengername", nullable = false)
    @JsonView(PublicView.class)
    private String PassengerName;
    @Column(name = "passengeremail")
    @JsonView(PublicView.class)
    private String PassengerEmail;
    @Column(name = "passengerphone", unique = true, nullable = false)
    @JsonView(PublicView.class)
    private BigInteger PassengerPhone;
    @Column(name = "passengerLocation")
    @JsonView(PublicView.class)
    private String PassengerLocation;
    @Column(name = "PassengerPassword")
    @JsonView(PrivateView.class)
    private String PassengerPassword;
    @Column(name = "PassengerIsActive")
    @JsonView(PublicView.class)
    private Boolean PassengerIsActive=true;
    @Column(name = "PassengerLastLogin")
    @JsonView(PublicView.class)
    private LocalDateTime PassengerLastLogin;
    @Column(name = "PassengerCreatedAt")
    @JsonView(PublicView.class)
    private LocalDateTime PassengerCreatedAt=LocalDateTime.now();
    @Column(name = "LastUpdatedTime")
    @JsonView(PublicView.class)
    private LocalDateTime LastUpdatedTime;
    @Column(name = "PassengerDeletedTime")
    @JsonView(PublicView.class)
    private LocalDateTime PassengerDeletedTime;
    @Column(name = "TokenId")
    @JsonView(PrivateView.class)
    private String TokenId;
    @Column(name = "LoginTime")
    @JsonView(PrivateView.class)
    private LocalDateTime LoginTime;
    @Column(name = "Remarks")
    private String Remarks;

    @JsonView(PublicView.class)
    @ManyToOne
    @JoinColumn(name = "StopId", nullable = false)
    private stops StopId;
    @JsonView(PublicView.class)
    @ManyToOne
    @JoinColumn(name = "CompanyId", nullable = false)
    private company CompanyId;

    
}
