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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.travelease.travelease.model.companymodel.company;
import com.travelease.travelease.service.CompanyService;

@CrossOrigin(origins = "${crossorigin}")
@RestController
@RequestMapping("/travelease/")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Value("${crossorigin}")
	private String crossorigin;

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

    //Edit company	
	@PutMapping("/Company")
	public ResponseEntity<String> updateCompany(@RequestBody company companyDetails) throws Exception{
		String response=companyService.updateCompany(companyDetails);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

    //bind company
    @PutMapping("/BindCompany")
	public ResponseEntity<String> BindCompany(@RequestBody company company) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(companyService.BindCompany(company));
	}

    // delete Company
	@DeleteMapping("/Company")
	public ResponseEntity<String> deleteCompany(@RequestBody company company) throws Exception{
		String response=companyService.DeleteCompany(company);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

    //company file upload
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

    //check company name
    @PostMapping("/CheckCompanyByName")
	public  ResponseEntity<Boolean> CheckCompanyByName(@RequestParam("companyname") String companyname) {
		return ResponseEntity.status(HttpStatus.OK).body(companyService.CheckCompanyByName(companyname));
	}

    //check company phone 
    @PostMapping("/CheckCompanyByPhone")
	public  ResponseEntity<Boolean> CheckCompanyByPhone(@RequestParam("companyphone") BigInteger companyphone) {
		return ResponseEntity.status(HttpStatus.OK).body(companyService.CheckCompanyByPhone(companyphone));
	}

    //check company email
    @PostMapping("/CheckCompanyByEmail")
	public  ResponseEntity<Boolean> CheckCompanyByEmail(@RequestParam("companyemail") String companyemail) {
		return ResponseEntity.status(HttpStatus.OK).body(companyService.CheckCompanyByEmail(companyemail));
	}
}
