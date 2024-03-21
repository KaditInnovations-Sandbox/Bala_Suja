package com.travelease.travelease.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.travelease.travelease.model.companymodel.company;
import com.travelease.travelease.service.CompanyService;

@CrossOrigin(origins = "http://192.168.20.26:4200")
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

	//get all active Company details
    @GetMapping("/ActiveCompany")
    public List<company> getAllActiveCompany(){
        return companyService.getAllActiveCompany();
    }

    //get all inactive Company details 
    @GetMapping("/InactiveCompany")
    public List<company> getAllInactiveCompany(){
        return companyService.getAllInactiveCompany();
    }

    //create company
    @PostMapping("/Company")
	public ResponseEntity<String> createCompany(@RequestBody company company) throws Exception {
		return ResponseEntity.status(HttpStatus.CREATED).body(companyService.createCompany(company));
	}

    //Edit for company	
	@PutMapping("/Company")
	public ResponseEntity<String> updateCompany(@RequestBody company companyDetails) throws Exception{
		String response=companyService.updateCompany(companyDetails);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

    //bind company
    @PutMapping("/BindCompany")
	public ResponseEntity<String> BindCompany(@RequestBody company company) throws Exception{
		String response=companyService.BindCompany(company);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

    // delete Company
	@DeleteMapping("/Company")
	public ResponseEntity<String> deleteCompany(@RequestBody company company) throws Exception{
		String response=companyService.DeleteCompany(company);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PostMapping("/Companyupload")
    public ResponseEntity<String> uploadCsv(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Please upload a CSV file!", HttpStatus.BAD_REQUEST);
        }

        try {
            companyService.saveCompanyFromCsv(file);
            return new ResponseEntity<>("File uploaded successfully!", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to upload file: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
