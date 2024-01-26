package com.travelease.travelease.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travelease.travelease.model.loginmodel.AdminLogin;
import com.travelease.travelease.service.AdminService;
import com.travelease.travelease.service.LoginService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/travelease/")
public class LoginController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private LoginService loginService;

    //admin Login
    @GetMapping("/Adminlogin")
	public ResponseEntity<Object> checkAdminExistence(@RequestPart AdminLogin adminLogin,@RequestPart String key) throws Exception{
        String response = adminService.adminLogin(adminLogin, key);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

    //forgot password and OTP generation
    @GetMapping("/ForgotPassword/{email}")
    public String ForgotPassword(@PathVariable String email) throws Exception{
        String access=loginService.forgotPassword(email);
        return access;
    }

    //verify OTP
    @PostMapping("/VerifyOTP")
	public ResponseEntity<Boolean> VerifyOTP(@RequestPart String OTP,@RequestPart String email) throws Exception {
		Boolean response=loginService.verifyOtp(email,OTP);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
    
}
