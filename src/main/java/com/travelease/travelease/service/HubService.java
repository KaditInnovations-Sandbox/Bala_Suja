package com.travelease.travelease.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.travelease.travelease.exception.ResourceNotFoundException;
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
        if(isvehicle==null){
            // vehicle.setVehicleRegistered(LocalDateTime.now());
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

    //get all active vehicle
    public List<Vehicle> getAllActiveVehicle(){
        return vehicleRepository.findByAccessTrue();
    }

    //get all active vehicle
    public List<Vehicle> getAllVehicle(){
        return vehicleRepository.findAll();
    }


    //get all inactive vehicle
    public List<Vehicle> getAllInactiveVehicle(){
        return vehicleRepository.findByAccessFalse();
    }

    //Grand Access 
    public String bindVehicle(String vehicleNumber){
        Vehicle vehicle=vehicleRepository.findByVehicleNumber(vehicleNumber);
        if(vehicle==null){
            throw new ResourceNotFoundException("Vehicle Not found");
        }else{
            vehicle.setVehicleAccess(true);
            vehicleRepository.save(vehicle);
            return "Access Updated";
        }       
    }
    
    //update vehicle
    public String  updateVehicle(Vehicle vehicleDetails)throws Exception{
        Vehicle vehicle=vehicleRepository.findByVehicleId(vehicleDetails.getVehicleId());
        if(vehicle==null){
            throw new ResourceNotFoundException("vehicle not found");
        }else{
            vehicle.setVehicleCapacity(vehicleDetails.getVehicleCapacity());
            vehicle.setVehicleNumber(vehicleDetails.getVehicleNumber());
            // vehicle.setVehicleRegistered(LocalDateTime.now());
            vehicleRepository.save(vehicle);
            return "updated";
        }
    }

}
