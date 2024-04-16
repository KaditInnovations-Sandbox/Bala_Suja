package com.travelease.travelease.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonView;
import com.travelease.travelease.model.adminmodel.Admin;
import com.travelease.travelease.service.AdminService;



@CrossOrigin(origins = "${crossorigin}")
@RestController
@RequestMapping("/travelease/")
public class AdminController {

    @Autowired
    private AdminService adminService;

	@Value("${crossorigin}")
	private String crossorigin;

    //get all admin details
	@JsonView(Admin.PublicView.class)
    @GetMapping("/Admin")
	public List<Admin> getAllAdmin(){
		return adminService.getAllAdmin();
	}

	// get admin by email 
	@JsonView(Admin.PublicView.class)
	@GetMapping("/AdminByEmail")
	public ResponseEntity<Object> getAdminById(@RequestBody String email) {
		return ResponseEntity.status(HttpStatus.CREATED).body(adminService.getAdminByEmail(email));
	}
    
    //create admin
    @PostMapping("/Admin")
	public ResponseEntity<String> createAdmin(@RequestBody Admin admin) throws Exception {
		return ResponseEntity.status(HttpStatus.CREATED).body(adminService.createAdmin(admin));
	}

	// update admin	
	@PutMapping("/Admin")
	public ResponseEntity<String> updateAdmin(@RequestBody Admin admindetails) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(adminService.updateAdmin(admindetails));
	}

    // delete admin
	@DeleteMapping("/Admin")
	public ResponseEntity<String> deleteAdmin(@RequestBody Admin admin) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(adminService.deleteAdmin(admin));
	}

	//update password
	@PutMapping("/updatePassword")
	public ResponseEntity<String> updatePassword(@RequestParam String password,@RequestParam String email) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(adminService.PasswordChange(email,password));
	}

	//grand access
	@PutMapping("/BindAdmin")
	public ResponseEntity<String> BindAdmin(@RequestBody Admin admin) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(adminService.bindAdmin(admin));
	}

	//get all active Company details
	@JsonView(Admin.PublicView.class)
    @GetMapping("/ActiveAdmin")
    public List<Admin> getAllActiveCompany(){
        return adminService.getAllActiveAdmin();
    }

    //get all inactive Company details 
	@JsonView(Admin.PublicView.class)
    @GetMapping("/InactiveAdmin")
    public List<Admin> getAllInactiveCompany(){
        return adminService.getAllInactiveAdmin();
    }

	//get admin by type
	@JsonView(Admin.PublicView.class)
    @GetMapping("/AdminByType")
	public List<Admin> getAdminByType(@RequestHeader(name = "AdminType") String type){
		return adminService.getAdminByType(type);
	}
	
	//get active admin by type
	@JsonView(Admin.PublicView.class)
    @GetMapping("/ActiveAdminByType")
	public List<Admin> getActiveAdminByType(@RequestHeader(name = "AdminType") String type){
		return adminService.getActiveAdminByType(type);
	}

	//get Inactive admin by type
	@JsonView(Admin.PublicView.class)
    @GetMapping("/InactiveAdminByType")
	public List<Admin> getInactiveAdminByType(@RequestHeader(name = "AdminType") String type){
		return adminService.getInactiveAdminByType(type);
	}

	//file upload
	@PostMapping("/AdminUpload")
    public ResponseEntity<String> uploadContractDriverCsv(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Please upload a CSV file!", HttpStatus.BAD_REQUEST);
        }

        try {
            return ResponseEntity.status(HttpStatus.OK).body(adminService.saveAdminFromCsv(file)+" Rows Added Successfully");
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to upload file: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	//Check Admin by email
	@GetMapping("/CheckAdminByEmail")
	public  ResponseEntity<Boolean> CheckAdminByEmail(@RequestParam("email") String email) {
		return ResponseEntity.status(HttpStatus.OK).body(adminService.CheckAdminByEmail(email));
	}

	//Check Admin by phone
	@GetMapping("/CheckAdminByEmail")
	public  ResponseEntity<Boolean> CheckAdminByPhone(@RequestParam("phone") BigInteger phone) {
		return ResponseEntity.status(HttpStatus.OK).body(adminService.CheckAdminByPhone(phone));
	}
    
}
