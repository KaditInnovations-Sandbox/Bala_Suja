package com.travelease.travelease.service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travelease.travelease.dto.OtpDto;
import com.travelease.travelease.exception.ResourceNotFoundException;
import com.travelease.travelease.model.adminmodel.Admin;
import com.travelease.travelease.model.loginmodel.AdminLogin;
import com.travelease.travelease.repository.AdminLoginRepository;
import com.travelease.travelease.repository.AdminRepository;
import com.travelease.travelease.util.OTPStorage;

@Service
public class LoginService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AdminLoginRepository adminLoginRepository;

    @Autowired
    private EmailService emailService;

    public String AdminforgotPassword(String email) throws Exception {
        Admin admin=adminRepository.findByAdminEmail(email);
        if(admin!=null){
            String otp=generateOTP(6);
            LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(5);
            OTPStorage.storeOtp(email, otp, expirationTime);
            String subject = "OTP from TravelEase ";
            String toEmail = email;
            String body="Your One-Time Password(OTP) is :<br><h2>\n"+otp+"<h2>";
            emailService.sendMail(toEmail, subject, body);
            return ("success");
        }else{
            throw new Exception("Email is wrong");
        }
    }

    //OTP generation method
    public static String generateOTP(int length) {  
        String numbers = "0123456789";  
        Random rndm_method = new Random();  
        char[] otp = new char[length];  
        for (int i = 0; i < length; i++) {  
            otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length()));  
        }  
        return new String(otp);  
    }  

    //verify otp
    public Boolean verifyOtp(Map<String,Object> verifyAdmin) {
        OtpDto otpData = OTPStorage.getOtpData((String)verifyAdmin.get("email"));
        if (otpData != null && LocalDateTime.now().isBefore(otpData.getExpirationTime())) {
            return otpData.getOtp().equals((String)verifyAdmin.get("otp"));
        } else {
            throw new ResourceNotFoundException("Time Expired"); // OTP expired or not found
        }
    }

    public String AdminLogOut(String token) {
        AdminLogin adminLogin=adminLoginRepository.findByTokenId(token);
        if (adminLogin == null) {
            throw new ResourceNotFoundException("AdminLogin not found");
        }
        adminLoginRepository.delete(adminLogin);
        return "Deleted Successfully";
    }
    
}
