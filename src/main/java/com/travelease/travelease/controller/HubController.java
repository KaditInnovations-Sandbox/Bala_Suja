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
import org.springframework.web.bind.annotation.ExceptionHandler;
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
import com.travelease.travelease.exception.DuplicatePhoneNumberException;
import com.travelease.travelease.exception.ResourceNotFoundException;
import com.travelease.travelease.model.hubmodel.Driver;
import com.travelease.travelease.model.hubmodel.Vehicle;
import com.travelease.travelease.repository.VehicleRepository;
import com.travelease.travelease.service.HubService;

@CrossOrigin(origins = "${crossorigin}")
@RestController
@RequestMapping("/travelease")
public class HubController {

    @Autowired 
    private HubService hubService;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Value("${crossorigin}")
	private String crossorigin;
    
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

    //grand access for vehicle
    @PutMapping("/BindVehicle")
	public ResponseEntity<String> BindVehicle(@RequestBody Vehicle vehicle) throws Exception{
		String response=hubService.bindVehicle(vehicle);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

    @PostMapping("/SevilaiVehicleUpload")
    public ResponseEntity<String> uploadVehicleCsv(@RequestParam("file") MultipartFile file) {
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

    @JsonView(Driver.PublicView.class)
    @GetMapping("/GetVehicle/Type/{VehicleType}")
    public List<Vehicle> GetVehicleType(@RequestHeader String VehicleType) {
        return hubService.getVehicleType(VehicleType);
    }

    @JsonView(Driver.PublicView.class)
    @GetMapping("/GetVehicle/Number/{VehicleNumber}")
    public Vehicle GetVehicleNumber(@RequestHeader String VehicleNumber){
        return hubService.getVehicleNumber(VehicleNumber);
    }
    

     //get all Driver details 
     @JsonView(Driver.PublicView.class)
     @GetMapping("/Driver")
     public List<Driver> getAllDriver(){
         return hubService.getAllDriver();
     }

    //get all Mapped Driver by type details 
    @JsonView(CombinedDriverVehiclePublicView.CombinedView.class)
    @GetMapping("/MappedDriver/{DriverType}")
    public List<Map<String, Object>> getAllMappedDriverByType(@RequestHeader String DriverType){
        return hubService.getAllMappedDriverByType(DriverType);
    }

    public class CombinedDriverVehiclePublicView {
        public interface CombinedView extends Driver.PublicView, Vehicle.PublicView {}
    }

    //get all Mapped Driver and Vehicle by type details
    @JsonView(CombinedDriverVehiclePublicView.CombinedView.class)
    @GetMapping("/AllDriverWithVehicle/{DriverType}")
    public ResponseEntity<List<Map<String, Object>>> getAllDriverWithVehicle(@RequestHeader String DriverType){
        return ResponseEntity.ok(hubService.getAllDriverWithVehicle(DriverType));
    }
    
     //get Driver based on type
    @JsonView(Driver.PublicView.class)
    @GetMapping("/GetDriver/Type/{DriverType}")
    public List<Driver> GetDriverType(@RequestHeader String DriverType) {
        return hubService.getDriverType(DriverType);
    }

    //get all active driver details
    @JsonView(Driver.PublicView.class)
    @GetMapping("/AllActiveDriver")
    public List<Driver> getAllActiveDriver(){
        return hubService.getAllActiveDriver();
    }

    //Active Driver Based on Type
    @JsonView(Driver.PublicView.class)
    @GetMapping("/ActiveDriver/Type/{DriverType}")
    public List<Driver> GetActiveDriverType(@RequestHeader String DriverType) {
        return hubService.getActiveDriverType(DriverType);
    }

    //InActive Driver Based on Type
    @JsonView(Driver.PublicView.class)
    @GetMapping("/InactiveDriver/Type/{DriverType}")
    public List<Driver> GetInactiveDriverType(@RequestHeader String DriverType) {
        return hubService.getInactiveDriverType(DriverType);
    }

    //get all inactive driver details
    @JsonView(Driver.PublicView.class) 
    @GetMapping("/AllInactiveDriver")
    public List<Driver> getAllInactiveDriver(){
        return hubService.getAllInactiveDriver();
    }

    //create Driver
    @PostMapping("/Driver")
    public ResponseEntity<String> createDriver( @RequestBody Map<String,Object> driver)throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(hubService.CreateDriver( driver));
    }       


    //update Driver
    @PutMapping("/Driver")
    public ResponseEntity<String> updateDriver( @RequestBody Map<String,Object> driver)throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(hubService.UpdateDriver( driver));
    }  

    //delete driver
    @DeleteMapping("/Driver")
    public ResponseEntity<String> deleteDriver(@RequestBody Driver driver)throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(hubService.DeleteDriver(driver));
    }

    //Bind driver
    @PutMapping("/BindDriver")
    public ResponseEntity<String> BindDriver(@RequestBody Driver driver)throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(hubService.BindDriver(driver));
    }

    @PostMapping("/SevilaiDriverUpload")
    public ResponseEntity<String> uploadSevilaiDriverCsv(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Please upload a CSV file!", HttpStatus.BAD_REQUEST);
        }

        try {
            return ResponseEntity.status(HttpStatus.OK).body(hubService.saveInsideDriverFromCsv(file)+" Rows Added Successfully");
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to upload file: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/ContractDriverUpload")
    public ResponseEntity<String> uploadContractDriverCsv(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Please upload a CSV file!", HttpStatus.BAD_REQUEST);
        }

        try {
            return ResponseEntity.status(HttpStatus.OK).body(hubService.saveOutsideDriverFromCsv(file)+" Rows Added Successfully");
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to upload file: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/getNotAssignVehicleCapacity")
    public List<String> GetNotAssignVehicleCapacity() {
        return hubService.findallVehicleCapacity();
    }

    //get vehicle capacity not mapped with driver
    @GetMapping("/getVehicleNotMappedWithDriver")
    public List<String> VehicleNotMappedWithDriver() {
        return hubService.VehicleNotMappedWithDriver();
    }
    

    @GetMapping("/GetvehicleNumberBasedVehicleCapacity")
    public List<String> GetvehicleCapacityBasedVehicleNumber(@RequestHeader("vehiclecapacity") String vehiclecapacity) {
        return hubService.vehicleCapacityBasedVehicleNumber(vehiclecapacity);
    }


    @GetMapping("/GetDriverNameBasedVehicleNumbr")
    public String GetDriverNameBasedVehicleNumbr(@RequestParam("vehicleNumber") String vehicleNumber) {
        return hubService.GetDriverNameBasedVehicleNumbr(vehicleNumber);
    }

    //Check Vehicle Number 
	@PostMapping("/CheckVehicleByVehicleNumber")
	public  ResponseEntity<Boolean> checkVehicleByVehicleNumber(@RequestParam("vehicleNumber") String vehicleNumber) {
		return ResponseEntity.status(HttpStatus.OK).body(hubService.checkVehicleByVehicleNumber(vehicleNumber));
	}
    

    //Check Admin by email
	@PostMapping("/CheckDriverByEmail")
	public  ResponseEntity<Boolean> CheckDriverByEmail(@RequestParam("email") String email) {
		return ResponseEntity.status(HttpStatus.OK).body(hubService.CheckDriverByEmail(email));
	}

	//Check Admin by phone
	@PostMapping("/CheckDriverByPhoneNumber")
	public  ResponseEntity<Boolean> CheckDriverByPhone(@RequestParam("phoneNumber") BigInteger phone) {
		return ResponseEntity.status(HttpStatus.OK).body(hubService.CheckDriverByPhone(phone));
	}


}
