package com.travelease.travelease.model.loginmodel;

import java.time.LocalDateTime;

import com.travelease.travelease.model.adminmodel.Admin;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admin_login_seq")
    @SequenceGenerator(name = "admin_login_seq", sequenceName = "admin_login_sequence", allocationSize = 1)
    private Long AdminLoginId;

    @ManyToOne
    @JoinColumn(name = "AdminId",nullable = false)
    private Admin AdminId;

    @Column(name = "Tokenid")
    private String TokenId;

    @Column(name = "LoginTime")
    private LocalDateTime LoginTime;

    @Column(name = "DeviceType")
    private String DeviceType;
    
}
