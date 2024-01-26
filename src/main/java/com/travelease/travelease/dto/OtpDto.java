package com.travelease.travelease.dto;

import java.time.LocalDateTime;

public class OtpDto {
     private final String otp;
    private final LocalDateTime expirationTime;

    public OtpDto(String otp, LocalDateTime expirationTime) {
        this.otp = otp;
        this.expirationTime = expirationTime;
    }

    public String getOtp() {
        return otp;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }    
}
