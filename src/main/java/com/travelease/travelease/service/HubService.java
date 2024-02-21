package com.travelease.travelease.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.travelease.travelease.exception.ResourceNotFoundException;
import com.travelease.travelease.model.adminmodel.Admin;
import com.travelease.travelease.model.hubmodel.Vehicle;
import com.travelease.travelease.repository.DriverRepository;
import com.travelease.travelease.repository.VehicleRepository;

@Service
public class HubService {
    
    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    //vehicle Create function
    public String CreateVehicle(Vehicle vehicle)throws Exception{
        Vehicle isvehicle=vehicleRepository.findByVehicleNumber(vehicle.getVehicleNumber());
        System.out.println(isvehicle+"*****"+vehicle);
        if(isvehicle==null){
            vehicle.setVehicleRegistered(LocalDateTime.now());
            vehicle.setVehicleAccess(true);
            vehicleRepository.save(vehicle);
            return "created";
        }else{
            throw new Exception();
        }
        
    }

    //vehicle Remove access
    public String DeleteVehicle(String vehiclenumber) throws Exception{
        Vehicle vehicle=vehicleRepository.findByVehicleNumber(vehiclenumber);
        if(vehicle==null){
            throw new ResourceNotFoundException("Vehicle Not found");
        }else{
            vehicle.setVehicleAccess(false);
            vehicleRepository.save(vehicle);
            return "Deleted";
        }       
        
    }

    //get all vehicle
    public List<Vehicle> getAllVehicle(){
        return vehicleRepository.findByAccessTrue();
    }

    //update vehicle
    public String  updateVehicle(Vehicle vehicleDetails)throws Exception{
        Vehicle vehicle=vehicleRepository.findByVehicleNumber(vehicleDetails.getVehicleNumber());
        if(vehicle==null){
            throw new ResourceNotFoundException("vehicle not found");
        }else{
            vehicle.setVehicleCapacity(vehicleDetails.getVehicleCapacity());
            vehicle.setVehicleRegistered(LocalDateTime.now());
            vehicleRepository.save(vehicle);
            return "updated";
        }
    }

}
