package com.travelease.travelease.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

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
import com.travelease.travelease.model.companymodel.company;
import com.travelease.travelease.model.passengermodel.passenger;
import com.travelease.travelease.model.routemodel.route;
import com.travelease.travelease.service.PassengerService;

@CrossOrigin(origins = "${crossorigin}")
@RestController
@RequestMapping("/travelease/")
public class PassengerController {
    
    @Autowired
    private PassengerService passengerService;

    @Value("${crossorigin}")
	private String crossorigin;

    public class CombinedPassengerPublicView {
        public interface CombinedView extends passenger.PublicView, company.PublicView, route.PublicView{}
    }
   

    //get all Passenger details
    @JsonView(CombinedPassengerPublicView.CombinedView.class)
    @GetMapping("/Passenger")
	public List<passenger> getAllPassengerDetatils(){
		return passengerService.getAllPassengerDetails();
	}

	// get passenger based on company name
	@GetMapping("/CompanyBasedPassenger")
    public List<passenger> getCompanyBasedPassenger(@RequestParam String companyname){
        return passengerService.getCompanyBasedPassenger(companyname);
    }

	//get all active Passenger details
    @GetMapping("/ActivePassenger")
    public List<passenger> getAllActivePassenger(){
        return passengerService.getAllActivePassenger();
    }

    //get all inactive Passenger details 
    @GetMapping("/InactivePassenger")
    public List<passenger> getAllInactivePassenger(){
        return passengerService.getAllInactivePassenger();
    }

    //create passenger
    @PostMapping("{companyname}/Passenger")
	public ResponseEntity<String> createPassenger(@RequestHeader String companyname,@RequestBody Map<String,Object> passenger) throws Exception {
		return ResponseEntity.status(HttpStatus.CREATED).body(passengerService.createPassenger(companyname, passenger));
	}

    //Edit for passenger	
	@PutMapping("/Passenger")
	public ResponseEntity<String> updateCompany(@RequestBody Map<String,Object> passengerDetails) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(passengerService.updatePassenger(passengerDetails));
	}

    //bind company
    @PutMapping("/BindPassenger")
	public ResponseEntity<String> BindCompany(@RequestBody passenger passenger) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(passengerService.BindPassenger(passenger));
	}

    // delete Passenger
	@DeleteMapping("/Passenger")
	public ResponseEntity<String> deletePassenger(@RequestBody passenger passenger) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(passengerService.DeletePassenger(passenger));
	}

	@PostMapping("/Passengerupload/{companyname}")
    public ResponseEntity<String> uploadCsv(@RequestHeader String companyname,@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Please upload a CSV file!", HttpStatus.BAD_REQUEST);
        }

        try {
            passengerService.savePassengerFromCsv(file,companyname);
            return new ResponseEntity<>("File uploaded successfully!", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to upload file: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //get all inactive Passenger details 
    @JsonView(CombinedPassengerPublicView.CombinedView.class)
    @GetMapping("/RouteBasedPassenger")
    public List<passenger> getRouteBasedPassenger(@RequestParam String routeid) throws Exception{
       return passengerService.getRouteBasedPassenger(routeid);
    }

    @GetMapping("/RouteBasedPassengerCount")
    public int getRouteBasedPassengerCount(@RequestParam String routeid) throws Exception{
       return passengerService.getRouteBasedPassengerCount(routeid);
    }

    //Check Passenger by email
	@PostMapping("/CheckPassengerByEmail")
	public  ResponseEntity<Boolean> CheckPassengerByEmail(@RequestParam("email") String email) {
		return ResponseEntity.status(HttpStatus.OK).body(passengerService.CheckPassengerByEmail(email));
	}

	//Check Passenger by phone
	@PostMapping("/CheckPassengerByPhoneNumber")
	public  ResponseEntity<Boolean> CheckPassengerByPhone(@RequestParam("phoneNumber") BigInteger phone) {
		return ResponseEntity.status(HttpStatus.OK).body(passengerService.CheckPassengerByPhone(phone));
	}
}
