package com.travelease.travelease.dto;

import java.math.BigInteger;

import lombok.Data;

@Data
public class AdminDriverDto {
    private String AdminName;
    private String AdminEmail;
    private BigInteger AdminPhone;
    private String Role;
    
}
