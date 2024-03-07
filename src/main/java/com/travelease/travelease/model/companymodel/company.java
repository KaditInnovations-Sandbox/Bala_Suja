package com.travelease.travelease.model.companymodel;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.validator.constraints.UniqueElements;

import com.fasterxml.jackson.annotation.JsonFormat;

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
@Table(name = "company")
public class company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "companyid")
    private long companyId;
    @Column(name = "companyname", unique = true, nullable = false)
    private String companyName;
    @Column(name = "companyemail")
    private String companyEmail;
    @Column(name = "companyphone", nullable = false)
    private BigInteger companyPhone;
    @Column(name = "companypoc")
    private String companyPoc;
    @Column(name = "companycreatedat")
    private LocalDateTime CompanyCreatedAt = LocalDateTime.now();
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "companystartdate")
    private LocalDate CompanyStartDate;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "companyenddate")
    private LocalDate CompanyEndDate;
    @Column(name = "companyisactive")
    private Boolean CompanyIsActive = true;
    @Column(name = "Companydeletedtime")
    private LocalDateTime CompanyDeletedTime;
    @Column(name = "Companylastupdatedtime")
    private LocalDateTime CompanyLastUpdatedTime;

}
