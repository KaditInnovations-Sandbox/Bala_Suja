package com.travelease.travelease.controller;


import java.util.List;
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

    // //admin Login via email
    // @PostMapping("/AdminEmaillogin")
	// public ResponseEntity<Object> checkAdminEmailExistence(@RequestBody AdminLogin adminLogin) throws Exception{
    //     String response=adminService.adminEmailLogin(adminLogin);
    //     return ResponseEntity.status(HttpStatus.OK).body(response);   
	// }

    // //admin Login via phone
    // @PostMapping("/AdminPhonelogin")
	// public ResponseEntity<Object> checkAdminPhoneExistence(@RequestBody AdminLogin adminLogin) throws Exception{
    //     String response=adminService.adminPhoneLogin(adminLogin);
    //     return ResponseEntity.status(HttpStatus.OK).body(response);   
	// }

    //forgot password and OTP generationl
    @PostMapping("/ForgotPassword")
    public String ForgotPassword(@RequestBody String email) throws Exception{
        String access=loginService.forgotPassword(email);
        return access;
    }

    //verify OTP
    @PostMapping("/VerifyOTP")
	public ResponseEntity<Boolean> VerifyOTP(@RequestBody String OTP,@RequestBody String email) throws Exception {
		Boolean response=loginService.verifyOtp(email,OTP);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
    

}
