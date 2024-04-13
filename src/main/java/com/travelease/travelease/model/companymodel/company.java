package com.travelease.travelease.model.companymodel;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.validator.constraints.UniqueElements;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
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
@Table(name = "company")
public class company {

    public interface PublicView {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "companyIdGenerator")
    @GenericGenerator(
            name = "companyIdGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "company_sequence"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "companyid")
    @JsonView(PublicView.class)
    private long companyId;
    @Column(name = "companyname", unique = true, nullable = false)
    @JsonView(PublicView.class)
    private String companyName;
    @Column(name = "companyemail", unique = true)
    @JsonView(PublicView.class)
    private String companyEmail;
    @Column(name = "companyphone", unique = true, nullable = false)
    @JsonView(PublicView.class)
    private BigInteger companyPhone;
    @Column(name = "companypoc")
    @JsonView(PublicView.class)
    private String companyPoc;
    @Column(name = "companycreatedat")
    @JsonView(PublicView.class)
    private LocalDateTime CompanyCreatedAt = LocalDateTime.now();
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "companystartdate")
    @JsonView(PublicView.class)
    private LocalDate CompanyStartDate;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "companyenddate")
    @JsonView(PublicView.class)
    private LocalDate CompanyEndDate;
    @Column(name = "companyisactive")
    @JsonView(PublicView.class)
    private Boolean CompanyIsActive = true;
    @Column(name = "Companydeletedtime")
    @JsonView(PublicView.class)
    private LocalDateTime CompanyDeletedTime;
    @Column(name = "Companylastupdatedtime")
    @JsonView(PublicView.class)
    private LocalDateTime CompanyLastUpdatedTime;
    @Column(name = "Remarks")
    @JsonView(PublicView.class)
    private String Remarks;
}
