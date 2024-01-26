package com.travelease.travelease.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.travelease.travelease.model.adminmodel.Admin;
import com.travelease.travelease.service.AdminService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/travelease/")
public class AdminController {

    @Autowired
    private AdminService adminService;

    //get all admin details
    @GetMapping("/Admin")
	public List<Admin> getAllAdmin(){
		return adminService.getAllAdmin();
	}

	// get admin by email 
	@GetMapping("/AdminByEmail")
	public ResponseEntity<Object> getAdminById(@RequestBody String email) {
		Object responseObject = adminService.getAdminByEmail(email);
		return ResponseEntity.status(HttpStatus.CREATED).body(responseObject);
	}
    
    //create admin
    @PostMapping("/Admin")
	public ResponseEntity<String> createAdmin(@RequestPart Admin admin,@RequestPart String roleName) throws Exception {
		String response=adminService.createAdmin(admin,roleName);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	// update admin	
	@PutMapping("/Admin")
	public ResponseEntity<String> updateAdmin(@RequestBody Admin admindetails) throws Exception{
		String response=adminService.updateAdmin(admindetails);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

    // delete admin
	@DeleteMapping("/Admin")
	public ResponseEntity<String> deleteAdmin(@RequestBody String email) throws Exception{
		String response=adminService.deleteAdmin(email);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PutMapping("/updatePassword")
	public ResponseEntity<String> updatePassword(@RequestPart String password,@RequestPart String email) throws Exception{
		String response=adminService.PasswordChange(email,password);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
    
}
