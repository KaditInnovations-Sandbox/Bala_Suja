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

import com.travelease.travelease.exception.ResourceNotFoundException;
import com.travelease.travelease.model.hubmodel.Vehicle;
import com.travelease.travelease.repository.VehicleRepository;
import com.travelease.travelease.service.HubService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/travelease")
public class HubController {

    @Autowired 
    private HubService hubService;

    @Autowired
    private VehicleRepository vehicleRepository;
    
    //create vehicle
    @PostMapping("/Vehicle")
    public ResponseEntity<String> createVehicle(@RequestBody Vehicle vehicle)throws Exception{
        String response = hubService.CreateVehicle(vehicle);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //delete vehicle
    @DeleteMapping("/Vehicle")
    public ResponseEntity<String> deleteVehicle(@RequestBody Vehicle vehicle)throws Exception{
        String response=hubService.DeleteVehicle(vehicle);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //get all active vehicle details
    @GetMapping("/ActiveVehicle")
    public List<Vehicle> getAllActiveVehicle(){
        return hubService.getAllActiveVehicle();
    }

    //get all inactive vehicle details 
    @GetMapping("/InactiveVehicle")
    public List<Vehicle> getAllInactiveVehicle(){
        return hubService.getAllInactiveVehicle();
    }

    //get all vehicle details 
    @GetMapping("/Vehicle")
    public List<Vehicle> getAllVehicle(){
        return hubService.getAllVehicle();
    }


    //get vehicle by id
    @GetMapping("/VehicleByID")
    public ResponseEntity<Vehicle> getEmployeeById(@RequestBody Long id) {
		@SuppressWarnings("null")
        Vehicle vehicle = vehicleRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("vehicle not exist with id :" + id));
		return ResponseEntity.ok(vehicle);
	}

    // update vehicle	
	@PutMapping("/Vehicle")
	public ResponseEntity<String> updateVehicle(@RequestBody Vehicle vehicleDetails) throws Exception{
		String response=hubService.updateVehicle(vehicleDetails);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

    @PutMapping("/BindVehicle")
	public ResponseEntity<String> BindVehicle(@RequestBody Vehicle vehicle) throws Exception{
		String response=hubService.bindVehicle(vehicle);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

    @PostMapping("/SevilaiVehicleUpload")
    public ResponseEntity<String> uploadCsv(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Please upload a CSV file!", HttpStatus.BAD_REQUEST);
        }

        try {
            Integer line=hubService.saveVehicleFromCsv(file);
            return new ResponseEntity<>("File uploaded successfully!\n"+line+"Rows added successfully", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to upload file: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
