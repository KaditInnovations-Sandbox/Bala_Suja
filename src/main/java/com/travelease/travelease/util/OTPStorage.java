package com.travelease.travelease.util;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.travelease.travelease.dto.OtpDto;

public class OTPStorage {
    private static final Map<String, OtpDto> otpMap = new ConcurrentHashMap<>();

    public static void storeOtp(String email, String otp, LocalDateTime expirationTime) {
        otpMap.put(email, new OtpDto(otp, expirationTime));
    }

    public static OtpDto getOtpData(String email) {
        return otpMap.get(email);
    }

    public static void removeOtp(String email) {
        otpMap.remove(email);
    }
}
