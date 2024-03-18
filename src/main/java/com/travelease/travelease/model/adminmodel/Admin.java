package com.travelease.travelease.model.adminmodel;

import java.math.BigInteger;
import java.time.LocalDateTime;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.type.descriptor.jdbc.LongVarbinaryJdbcType;

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
@Table(name = "Admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "adminIdGenerator")
    @GenericGenerator(
            name = "adminIdGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "admin_sequence"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "AdminId")
    private Long AdminId;
    @Column(name = "AdminName")
    private String AdminName;
    @Column(name = "AdminEmail")
    private String AdminEmail;
    @Column(name = "AdminPhone") 
    private BigInteger AdminPhone;
    @Column(name = "AdminPassword")
    private String AdminPassword;
    @Column(name = "AdminCreatedAt")
    private LocalDateTime AdminCreatedAt = LocalDateTime.now();
    @Column(name = "AdminLastLogin")
    private LocalDateTime AdminLastLogin;
    @Column(name = "AdminIsActive")
    private Boolean AdminIsActive = true;
    @Column(name = "AdminLastUpdatedTime")
    private LocalDateTime AdminLastUpdatedTime;
    @Column(name = "AdminDeletedTime")
    private LocalDateTime AdminDeletedTime;


}
