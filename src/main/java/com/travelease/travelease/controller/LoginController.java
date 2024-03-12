package com.travelease.travelease.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.travelease.travelease.service.AdminService;
import com.travelease.travelease.service.LoginService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/travelease/")
public class LoginController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private LoginService loginService;

    //Admin Login
    @PostMapping("/AdminLogin")
	public ResponseEntity<Map<String,String>> AdminLogin(@RequestBody Map<String,String> adminLogin) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(adminService.adminLogin(adminLogin));   
	}

    //forgot password and OTP generationl
    @PostMapping("/AdminForgotPassword")
    public String AdminForgotPassword(@RequestBody String email) throws Exception{
        String access=loginService.AdminforgotPassword(email);
        return access;
    }

    //verify OTP
    @PostMapping("/VerifyOTP")
	public ResponseEntity<Boolean> VerifyOTP(@RequestBody String OTP,@RequestBody String email) throws Exception {
		Boolean response=loginService.verifyOtp(email,OTP);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
    
    //Passenger Login
    @PostMapping("/PassengerLogin")
	public ResponseEntity<Map<String,Object>> PassengerLogin(@RequestBody Map<String,Object> passengerLogin) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(adminService.passengerLogin(passengerLogin));   
	}

    //Driver Login
    @PostMapping("/DriverLogin")
	public ResponseEntity<Map<String,Object>> DriverLogin(@RequestBody Map<String,Object> driverLogin) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(adminService.driverLogin(driverLogin));   
	}

}
