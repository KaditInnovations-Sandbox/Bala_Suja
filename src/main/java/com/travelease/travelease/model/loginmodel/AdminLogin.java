package com.travelease.travelease.model.loginmodel;

import java.math.BigInteger;
import java.time.LocalDateTime;

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
@Table(name = "Adminlogin")
public class AdminLogin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AdminId")
    private Long AdminId;

    @Column(name = "AdminEmail")
    private String AdminEmail;

    @Column(name = "AdminPhone")
    private BigInteger AdminPhone;

    @Column(name = "AdminPassword")
    private String AdminPassword;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    // @ManyToOne
    // @JoinColumn(name = "roleAccess", nullable = false)
    // private Role role;
    
}
