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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
