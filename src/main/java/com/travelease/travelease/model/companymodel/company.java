package com.travelease.travelease.model.companymodel;

import java.math.BigInteger;
import java.time.LocalDateTime;

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
@Table(name = "company")
public class company {

    @Id
    @GeneratedValue
    @Column(name = "companyid")
    private long companyId;
    @Column(name = "companyname")
    private String companyName;
    @Column(name = "companyemail")
    private String companyEmail;
    @Column(name = "companyphone")
    private BigInteger companyPhone;
    @Column(name = "companypoc")
    private String companyPoc;
    @Column(name = "companyactive")
    private Boolean active;
    @Column(name = "companydateregistered")
    private LocalDateTime CompanyDateRegistered;
    @Column(name = "companylastlogin")
    private LocalDateTime CompanyLastLogin;
    @Column(name = "companyisactive")
    private Boolean CompanyIsActive;

}
