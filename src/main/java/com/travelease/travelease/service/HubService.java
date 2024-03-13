package com.travelease.travelease.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.travelease.travelease.exception.ResourceNotFoundException;
import com.travelease.travelease.model.hubmodel.Vehicle;
import com.travelease.travelease.repository.VehicleRepository;

@Service
public class HubService {
    
    @Autowired
    private VehicleRepository vehicleRepository;

    //vehicle Create function
    public String CreateVehicle(Vehicle vehicle)throws Exception{
        Vehicle isvehicle=vehicleRepository.findByVehicleNumber(vehicle.getVehicleNumber());
        if(isvehicle==null){
            vehicle.setVehicleType("sevilai");
            vehicleRepository.save(vehicle);
            return "created";
        }else{
            throw new Exception("Vehicle already exists");
        }
        
    }

    //vehicle Remove access
    public String DeleteVehicle(Vehicle vehicle) throws Exception{
        Vehicle vehicles=vehicleRepository.findByVehicleNumber(vehicle.getVehicleNumber());
        System.out.println(vehicle);
        if(vehicles==null){
            throw new ResourceNotFoundException("Vehicle Not found");
        }else{
            vehicles.setVehicleIsActive(false);
            vehicles.setVehicleDeletedTime(LocalDateTime.now());
            vehicleRepository.save(vehicles);
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
    public String bindVehicle(Vehicle vehicles){
        Vehicle vehicle=vehicleRepository.findByVehicleNumber(vehicles.getVehicleNumber());
        if(vehicle==null){
            throw new ResourceNotFoundException("Vehicle Not found");
        }else{
            vehicle.setVehicleIsActive(true);
            vehicle.setVehicleDeletedTime(null);
            vehicle.setLastUpdatedTime(LocalDateTime.now());
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
            vehicle.setLastUpdatedTime(LocalDateTime.now());
            vehicleRepository.save(vehicle);
            return "updated";
        }
    }

    //Vehicle data read from csv
    public Integer saveVehicleFromCsv(MultipartFile file) throws IOException {
        List<Vehicle> vehicles = new ArrayList<>();
        Integer count=0;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                count++;
                String[] data = line.split(",");
                Vehicle vehicle = new Vehicle();
                vehicle.setVehicleCapacity(data[0]);
                vehicle.setVehicleNumber(data[1]);
                vehicle.setVehicleType("sevilai");
                vehicles.add(vehicle);
            }
        }
        vehicleRepository.saveAll(vehicles);
        return count;
    }

}
