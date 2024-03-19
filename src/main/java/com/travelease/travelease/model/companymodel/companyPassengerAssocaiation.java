package com.travelease.travelease.model.companymodel;

import com.travelease.travelease.model.passengermodel.passenger;

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
@Table(name = "companypassengerassociation")
public class companyPassengerAssocaiation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CompanyPassengerId")
    private Long CompanyPassengerId;

    @ManyToOne
    @JoinColumn(name = "companyid", nullable = false)
    private company companyId;

    @ManyToOne
    @JoinColumn(name = "Passengerid", nullable = false)
    private passenger PassengerId;

    
}
