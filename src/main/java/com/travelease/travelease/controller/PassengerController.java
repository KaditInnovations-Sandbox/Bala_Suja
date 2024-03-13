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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.travelease.travelease.model.companymodel.company;
import com.travelease.travelease.model.passengermodel.passenger;
import com.travelease.travelease.service.PassengerService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/travelease/")
public class PassengerController {
    
    @Autowired
    private PassengerService passengerService;

    //get all Passenger details
    @GetMapping("/Passenger")
	public List<passenger> getAllPassengerDetatils(){
		return passengerService.getAllPassengerDetails();
	}

	// //get all active Passenger details
    // @GetMapping("/ActivePassenger")
    // public List<passenger> getAllActivePassenger(){
    //     return passengerService.getAllActivePassenger();
    // }

    // //get all inactive Passenger details 
    // @GetMapping("/InactivePassenger")
    // public List<passenger> getAllInactivePassenger(){
    //     return passengerService.getAllInactivePassenger();
    // }

    // //create passenger
    // @PostMapping("/Passenger")
	// public ResponseEntity<String> createPassenger(@RequestBody passenger passenger) throws Exception {
	// 	return ResponseEntity.status(HttpStatus.CREATED).body(companyService.createCompany(company));
	// }

    // //Edit for company	
	// @PutMapping("/Company")
	// public ResponseEntity<String> updateCompany(@RequestBody company companyDetails) throws Exception{
	// 	String response=companyService.updateCompany(companyDetails);
	// 	return ResponseEntity.status(HttpStatus.OK).body(response);
	// }

    // //bind company
    // @PutMapping("/BindCompany")
	// public ResponseEntity<String> BindCompany(@RequestBody String companyName) throws Exception{
	// 	String response=companyService.BindCompany(companyName);
	// 	return ResponseEntity.status(HttpStatus.OK).body(response);
	// }

    // // delete Company
	// @DeleteMapping("/Company")
	// public ResponseEntity<String> deleteCompany(@RequestBody String companyName) throws Exception{
	// 	String response=companyService.DeleteCompany(companyName);
	// 	return ResponseEntity.status(HttpStatus.OK).body(response);
	// }
}
