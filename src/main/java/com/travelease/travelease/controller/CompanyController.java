package com.travelease.travelease.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.travelease.travelease.model.adminmodel.Admin;
import com.travelease.travelease.model.companymodel.company;
import com.travelease.travelease.service.CompanyService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/travelease/")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    //get all Company details
    @GetMapping("/Company")
	public List<company> getAllCompanyDetatils(){
		return companyService.getAllCompanyDetails();
	}

    //create company
    @PostMapping("/Company")
	public ResponseEntity<String> createAdmin(@RequestBody company company) throws Exception {
		String response=companyService.createCompany(company);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	
    // delete Company
	@DeleteMapping("/Company")
	public ResponseEntity<String> deleteAdmin(@RequestBody String email) throws Exception{
		String response=companyService.deleteCompany(email);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
