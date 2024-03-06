package com.travelease.travelease.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	public List<passenger> getAllPassengerDetatils(@RequestParam Long CompanyId){
		return passengerService.getAllPassengerDetails(CompanyId);
	}

	// //get all active Passenger details
    // @GetMapping("/ActivePassenger")
    // public List<Passenger> getAllActivePassenger(){
    //     return passengerService.getAllActivePassenger();
    // }

    // //get all inactive Passenger details 
    // @GetMapping("/InactivePassenger")
    // public List<Passenger> getAllInactivePassenger(){
    //     return PassengerService.getAllInactivePassenger();
    // }

    // //create Passenger
    // @PostMapping("/Passenger")
	// public ResponseEntity<String> createAdmin(@RequestBody Passenger Passenger) throws Exception {
	// 	return ResponseEntity.status(HttpStatus.CREATED).body(PassengerService.createPassenger(Passenger));
	// }

    // //Edit for Passenger	
	// @PutMapping("/Passenger")
	// public ResponseEntity<String> updatePassenger(@RequestBody Passenger PassengerDetails) throws Exception{
	// 	String response=PassengerService.updatePassenger(PassengerDetails);
	// 	return ResponseEntity.status(HttpStatus.OK).body(response);
	// }

    // //bind Passenger
    // @PutMapping("/BindPassenger")
	// public ResponseEntity<String> BindPassenger(@RequestBody String PassengerName) throws Exception{
	// 	String response=PassengerService.BindPassenger(PassengerName);
	// 	return ResponseEntity.status(HttpStatus.OK).body(response);
	// }

    // // delete Passenger
	// @DeleteMapping("/Passenger")
	// public ResponseEntity<String> deletePassenger(@RequestBody String PassengerName) throws Exception{
	// 	String response=PassengerService.DeletePassenger(PassengerName);
	// 	return ResponseEntity.status(HttpStatus.OK).body(response);
	// }

}
