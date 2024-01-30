package com.travelease.travelease.controller;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.travelease.travelease.model.loginmodel.AdminLogin;
import com.travelease.travelease.service.AdminService;
import com.travelease.travelease.service.LoginService;

import org.springframework.web.bind.annotation.PathVariable;
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

    //admin Login
    @PostMapping("/Adminlogin")
	public ResponseEntity<Object> checkAdminExistence(
        @RequestParam("adminLogin.admin_email") String adminEmail,
        @RequestParam("adminLogin.admin_phone") BigInteger adminPhone,
        @RequestParam("adminLogin.admin_password") String adminPassword,
        @RequestParam("key") String key,
        @RequestParam(value = "file", required = false) MultipartFile file)
         throws Exception{
        String response = adminService.adminLogin(adminEmail,adminPhone,adminPassword, key);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

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
