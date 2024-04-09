package com.travelease.travelease.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.annotations.TimeZoneStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.travelease.travelease.exception.ResourceNotFoundException;
import com.travelease.travelease.model.hubmodel.Driver;
import com.travelease.travelease.model.hubmodel.DrivervehicleAssociation;
import com.travelease.travelease.model.hubmodel.Vehicle;
import com.travelease.travelease.repository.DriverRepository;
import com.travelease.travelease.repository.DriverVehicleAssociationRepository;
import com.travelease.travelease.repository.VehicleRepository;
import com.travelease.travelease.util.JwtUtils;

@Service
public class HubService {

    @Autowired
    private JwtUtils jwtUtils;

     
    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private DriverVehicleAssociationRepository driverVehicleAssociationRepository;

    @Autowired
    private Environment env;

    //vehicle Create function
    public String CreateVehicle(Vehicle vehicle)throws Exception{
        Vehicle isvehicle=vehicleRepository.findByVehicleNumber(vehicle.getVehicleNumber());
        if(isvehicle==null){
            vehicle.setVehicleType(env.getProperty("vehicle.type"));
            vehicleRepository.save(vehicle);
            return "created";
        }else{
            throw new Exception("Vehicle already exists");
        }
        
    }

    //vehicle Remove access
    public String DeleteVehicle(Vehicle vehicle) throws Exception{
        Vehicle vehicles=vehicleRepository.findByVehicleNumber(vehicle.getVehicleNumber());
        if(vehicles==null){
            throw new ResourceNotFoundException("Vehicle Not found");
        }else{
            vehicles.setVehicleIsActive(false);
            vehicles.setVehicleDeletedTime(LocalDateTime.now());
            vehicles.setRemarks(vehicle.getRemarks());
            vehicleRepository.save(vehicles);
            return "Deleted";
        }       
        
    }

    //get all active vehicle
    public List<Vehicle> getAllActiveVehicle(){
        return vehicleRepository.findByAccessTrue();
    }

    //get all vehicle
    public List<Vehicle> getAllVehicle(){
        return vehicleRepository.findAll();
    }


    //get all inactive vehicle
    public List<Vehicle> getAllInactiveVehicle(){
        return vehicleRepository.findByAccessFalse();
    }

    //Grand Access 
    public String bindVehicle(Vehicle vehicles){
        Vehicle vehicle=vehicleRepository.checkByVehicleNumber(vehicles.getVehicleNumber());
        if(vehicle==null){
            throw new ResourceNotFoundException("Vehicle Not found");
        }else{
            vehicle.setVehicleIsActive(true);
            vehicle.setVehicleDeletedTime(null);
            vehicle.setLastUpdatedTime(LocalDateTime.now());
            vehicle.setRemarks(null);
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


    //get all Driver
    public List<Driver> getAllDriver(){
        return driverRepository.findAll();
    }

    //get all active driver
    public List<Driver> getAllActiveDriver(){
        return driverRepository.findByAccessTrue();
    }

    //get all inactive driver
    public List<Driver> getAllInactiveDriver(){
        return driverRepository.findByAccessFalse();
    }

    //get Vehicle By Type
    public List<Vehicle> getVehicleType(String VehicleType) {
        return vehicleRepository.findByVehicleType(VehicleType);
    }

    //get Vehicle By Type
    public Vehicle getVehicleNumber(String VehicleNumber) {
        return vehicleRepository.findByVehicleNumber(VehicleNumber);
    }

    //Driver Create function
     public String CreateDriver(Map<String,Object> driverdetails)throws Exception{
        Driver isDriver=driverRepository.findByDriverPhone(new BigInteger(driverdetails.get("driver_phone").toString()));
        Driver driver=new Driver();
        Vehicle vehicle=vehicleRepository.findByVehicleNumber((String)driverdetails.get("vehicle_number"));
        if(driverdetails.get("driver_type").equals(env.getProperty("Insidedrivertype"))&& isDriver==null&&vehicle!=null){
            if(driverVehicleAssociationRepository.findDriverVehicleByVehicleId(vehicle.getVehicleId())==null){
                driver.setDriverName((String)driverdetails.get("driver_name"));
                driver.setDriverPhone(new BigInteger(String.valueOf(driverdetails.get("driver_phone"))));
                driver.setDriverEmail((String)driverdetails.get("driver_email"));
                driver.setDriverPassword(encodePassword((driverdetails.get("driver_phone")).toString()));
                driver.setDriverType(env.getProperty("Insidedrivertype"));
                driverRepository.save(driver);
                DrivervehicleAssociation drivervehicleAssociation= new DrivervehicleAssociation();
                drivervehicleAssociation.setDriverId(driver);
                drivervehicleAssociation.setVehicleId(vehicleRepository.findByVehicleNumber((String)driverdetails.get("vehicle_number")));
                driverVehicleAssociationRepository.save(drivervehicleAssociation);
                return env.getProperty("Insidedrivertype")+" Driver and Vehicle Mapped successfully";
            }else{
                throw new ResourceNotFoundException("Vehicle Mapped another driver");
            }   
        }else if(driverdetails.get("driver_type").equals(env.getProperty("Outsidedrivertype"))  && isDriver==null && vehicleRepository.findByVehicleNumber((String)driverdetails.get("vehicle_number"))==null){
            Vehicle newVehicle = new Vehicle();
            driver.setDriverName((String)driverdetails.get("driver_name"));
            driver.setDriverPhone(new BigInteger(String.valueOf(driverdetails.get("driver_phone"))));
            driver.setDriverEmail((String)driverdetails.get("driver_email"));
            driver.setDriverPassword(encodePassword((driverdetails.get("driver_phone")).toString()));
            driver.setDriverType(env.getProperty("Outsidedrivertype"));
            newVehicle.setVehicleCapacity((String)driverdetails.get("vehicle_capacity"));
            newVehicle.setVehicleNumber((String)driverdetails.get("vehicle_number"));
            newVehicle.setVehicleType(env.getProperty("Outsidedrivertype"));
            vehicleRepository.save(newVehicle);
            driverRepository.save(driver);
            DrivervehicleAssociation drivervehicleAssociation= new DrivervehicleAssociation();
            drivervehicleAssociation.setDriverId(driver);
            drivervehicleAssociation.setVehicleId(newVehicle);
            driverVehicleAssociationRepository.save(drivervehicleAssociation);            
            return env.getProperty("Outsidedrivertype")+" Driver Created";
        }else{
            throw new Exception("Driver already exists");
        }
    }

    // // Common method to read driver data from CSV
    // private List<Driver> readDriversFromCsv(MultipartFile file, String driverType, List<DrivervehicleAssociation> drivervehicleAssociations, List<Vehicle> vehicles) throws IOException {
    //     List<Driver> drivers = new ArrayList<>();
    //     try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
    //         String line;
    //         while ((line = reader.readLine()) != null) {
    //             String[] data = line.split(",");
    //             Driver driver = new Driver();
    //             driver.setDriverName(data[0]);
    //             driver.setDriverPhone(new BigInteger(data[1]));
    //             driver.setDriverEmail(data[2]);
    //             driver.setDriverPassword(encodePassword(data[1]));
    //             driver.setDriverType(driverType);
    //             Vehicle vehicle = vehicleRepository.findByVehicleNumber(data[4]);
    //             if (vehicle == null && driverType.equals(env.getProperty("Outsidedrivertype"))) {
    //                 Vehicle newVehicle = new Vehicle();
    //                 newVehicle.setVehicleCapacity(data[3]);
    //                 newVehicle.setVehicleNumber(data[4]);
    //                 newVehicle.setVehicleType(env.getProperty("Outsidedrivertype"));
    //                 vehicles.add(newVehicle);

    //                 DrivervehicleAssociation drivervehicleAssociation = new DrivervehicleAssociation();
    //                 drivervehicleAssociation.setDriverId(driver);
    //                 drivervehicleAssociation.setVehicleId(newVehicle);
    //                 drivervehicleAssociations.add(drivervehicleAssociation);
    //             } else if (vehicle != null && driverType.equals(env.getProperty("Insidedrivertype"))) {
    //                 DrivervehicleAssociation drivervehicleAssociation = new DrivervehicleAssociation();
    //                 drivervehicleAssociation.setDriverId(driver);
    //                 drivervehicleAssociation.setVehicleId(vehicle);
    //                 drivervehicleAssociations.add(drivervehicleAssociation);
    //             }

    //             drivers.add(driver);
    //         }
    //     }
    //     return drivers;
    // }

    // // Common method to process and save drivers
    // private void saveDrivers(List<Driver> drivers, List<Vehicle> vehicles, List<DrivervehicleAssociation> drivervehicleAssociations) {
    //     vehicleRepository.saveAll(vehicles);
    //     driverRepository.saveAll(drivers);
    //     driverVehicleAssociationRepository.saveAll(drivervehicleAssociations);
    // }

    // // Contract Driver data read from csv
    // public Integer saveContractDriverFromCsv(MultipartFile file) throws IOException {
    //     List<Vehicle> vehicles = new ArrayList<>();
    //     List<DrivervehicleAssociation> drivervehicleAssociations = new ArrayList<>();
    //     List<Driver> drivers = readDriversFromCsv(file, env.getProperty("Outsidedrivertype"), drivervehicleAssociations, vehicles);
    //     saveDrivers(drivers, vehicles, drivervehicleAssociations);
    //     return drivers.size();
    // }

    // // Sevilai Driver data read from csv
    // public Integer saveSevilaiDriverFromCsv(MultipartFile file) throws IOException {
    //     List<DrivervehicleAssociation> drivervehicleAssociations = new ArrayList<>();
    //     List<Vehicle> vehicles = new ArrayList<>();
    //     List<Driver> drivers = readDriversFromCsv(file, env.getProperty("Insidedrivertype"), drivervehicleAssociations, vehicles);
    //     saveDrivers(drivers, vehicles, drivervehicleAssociations);
    //     return drivers.size();
    // }


    //upload contract driver 
    public Integer saveOutsideDriverFromCsv(MultipartFile file) throws IOException {
        List<Driver> drivers = new ArrayList<>();
        List<Vehicle> vehicles=new ArrayList<>();
        List<DrivervehicleAssociation> drivervehicleAssociations=new ArrayList<>();
        Integer count=0;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                count++;
                String[] data = line.split(",");
                Driver driver=new Driver();
                driver.setDriverName(data[0]);
                driver.setDriverPhone(new BigInteger(data[1]));
                driver.setDriverEmail(data[2]);
                driver.setDriverPassword(encodePassword(data[1]));
                driver.setDriverType(env.getProperty("Outsidedrivertype"));
                Vehicle vehicle=vehicleRepository.findByVehicleNumber(data[4]);
                Vehicle newVehicle = new Vehicle();
                if (vehicle == null) {
                    newVehicle.setVehicleCapacity(data[3]);
                    newVehicle.setVehicleNumber(data[4]);
                    newVehicle.setVehicleType(env.getProperty("Outsidedrivertype"));
                    vehicles.add(newVehicle);
                    DrivervehicleAssociation drivervehicleAssociation = new DrivervehicleAssociation();
                    drivervehicleAssociation.setDriverId(driver);
                    drivervehicleAssociation.setVehicleId(newVehicle);
                    drivervehicleAssociations.add(drivervehicleAssociation);
                } 
                drivers.add(driver);
            }
        }
        driverRepository.saveAll(drivers);
        vehicleRepository.saveAll(vehicles);
        driverVehicleAssociationRepository.saveAll(drivervehicleAssociations);
        return count;
    }       

    //upload sevilai driver 
    public Integer saveInsideDriverFromCsv(MultipartFile file) throws IOException {
        List<Driver> drivers = new ArrayList<>();
        Integer count=0;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                count++;
                String[] data = line.split(",");
                Driver driver=new Driver();
                driver.setDriverName(data[0]);
                driver.setDriverPhone(new BigInteger(data[1]));
                driver.setDriverEmail(data[2]);
                driver.setDriverPassword(encodePassword(data[1]));
                driver.setDriverType(env.getProperty("Insidedrivertype"));
                drivers.add(driver);
            }
        }
        driverRepository.saveAll(drivers);
        return count;
    }       


    //Update Driver
    public String UpdateDriver(Map<String,Object> driverdetails)throws Exception{
        Driver driver=driverRepository.checkById(Long.valueOf(driverdetails.get("driver_id").toString()));
        DrivervehicleAssociation drivervehicleAssociation=driverVehicleAssociationRepository.findDriverVehicleByDriverId(Long.valueOf(driverdetails.get("DriverId").toString()));
        if(driver!=null){
            driver.setDriverName((String)driverdetails.get("driver_name"));
            driver.setDriverPhone(new BigInteger(String.valueOf(driverdetails.get("driver_phone"))));
            driver.setDriverEmail((String)driverdetails.get("driverEmail"));
            driver.setDriverPassword(encodePassword((driverdetails.get("driver_phone")).toString()));
            driver.setLastUpdatedTime(LocalDateTime.now());
            driverRepository.save(driver);
            drivervehicleAssociation.setDriverId(driver);
            if(driverdetails.get("driver_type").equals(env.getProperty("Insidedrivertype"))){
                drivervehicleAssociation.setVehicleId(vehicleRepository.findByVehicleNumber((String)driverdetails.get("vehicle_number")));
                driverVehicleAssociationRepository.save(drivervehicleAssociation);
                return env.getProperty("Insidedrivertype")+" Driver Updated";
            }else if(driverdetails.get("driver_type").equals(env.getProperty("Outsidedrivertype"))){
                Vehicle vehicle=vehicleRepository.findByVehicleId(drivervehicleAssociation.getVehicleId().getVehicleId());
                vehicle.setVehicleNumber((String)driverdetails.get("vechicle_number"));
                vehicle.setVehicleCapacity((String)driverdetails.get("vehicle_capacity"));
                vehicleRepository.save(vehicle);
                drivervehicleAssociation.setVehicleId(vehicle);
                driverVehicleAssociationRepository.save(drivervehicleAssociation);
                return env.getProperty("Outsidedrivertype")+" Driver Updated";
            }else{
                throw new Exception("DriverType missing");                
            }
            
        }else{
            throw new Exception("Driver already exists");
        }
    }


    //get Driver By Type
    public List<Driver> getDriverType(String DriverType) {
        return driverRepository.findByDriverType(DriverType);
    }

    //get Driver By Type
    public List<Driver> getActiveDriverType(String DriverType) {
        return driverRepository.findByActiveDriverType(DriverType);
    }

    //get Driver By Type
    public List<Driver> getInactiveDriverType(String DriverType) {
        return driverRepository.findByInactiveDriverType(DriverType);
    }

    //get all Mapped Driver
    public List<Map<String, Object>> getAllMappedDriverByType(String DriverType){
        return driverVehicleAssociationRepository.findDriversByType(DriverType);
    }

    //get all Driver with vehicle
    public List<Map<String, Object>> getAllDriverWithVehicle(String DriverType) {
        List<Driver> drivers = driverRepository.findByDriverType(DriverType);
        List<Map<String, Object>> driverAndVehicle = new ArrayList<>();
    
        for (Driver d : drivers) {
            Map<String, Object> DriverWithVehicle = new HashMap<>();
    
            DrivervehicleAssociation drivervehicleAssociation = driverVehicleAssociationRepository.findDriverVehicleByDriverId(d.getDriverId());
    
            if (drivervehicleAssociation != null) {
                DriverWithVehicle.put("Driver", d);
                System.out.println(drivervehicleAssociation.getVehicleId());
                DriverWithVehicle.put("Vehicle", vehicleRepository.findByVehicleId(drivervehicleAssociation.getVehicleId().getVehicleId()));
                driverAndVehicle.add(DriverWithVehicle); // Assuming drivervehicleAssociation has a getVehicle() method
            } else {
                DriverWithVehicle.put("Driver", d);
                DriverWithVehicle.put("Vehicle", "No Vehicle Associated");
                driverAndVehicle.add(DriverWithVehicle); // Or you can set this to null or some default value
            }
    
            
        }
    
        return driverAndVehicle;
    }
    
    
    //Driver Login
    // public Map<String,Object> driverLogin(Map<String,Object> driverLogin) throws Exception{
    //     Map<String, Object> resultMap = new HashMap<>();
    //     Driver driver=driverRepository.findByDriverPhone((BigInteger)driverLogin.get("phone"));
    //     if(driverLogin.get("token")==null && driver!=null && driver.getDriverIsActive()){
    //             if(verifyPassword((String)driverLogin.get("password"), driver.getDriverPassword())){
    //                 //token creation and store login table
    //                 DriverLogin logindriver=new DriverLogin();
    //                 logindriver.setDriver(driver);
    //                 logindriver.setTimestamp(LocalDateTime.now());
    //                 logindriver.setTokenId(jwtUtils.generateJwtDriver(driver));
    //                 resultMap.put("token", jwtUtils.generateJwtDriver(driver));
    //                 driverLoginRepository.save(logindriver);
    //                 driver.setDriverLastLogin(LocalDateTime.now());
    //                 driverRepository.save(driver);
    //                 return resultMap; 
    //             }else{
    //                 throw new Exception();
    //                 //Exception Driver is not match
    //             }
    //     }else if(driverLogin.get("token")!=null && driver!=null && driver.getDriverIsActive()){
    //         resultMap.put("email", jwtUtils.verify((String)driverLogin.get("token")));
    //         driver.setDriverLastLogin(LocalDateTime.now());
    //         driverRepository.save(driver);
    //         return resultMap;

    //     }else{
    //         throw new Exception();
    //         //Exception Driver is not active or not found
    //     }
    // }

    //password encode method -- 
    public static String encodePassword(String rawPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(rawPassword.getBytes());

            // Convert bytes to hexadecimal format
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : hashedBytes) {
                stringBuilder.append(String.format("%02x", b));
            }

            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error encoding password", e);
        }  
    }

    //verify password
    public static boolean verifyPassword(String rawPassword, String storedHash) {
        // Hash the entered password using the same algorithm
        String enteredHash = encodePassword(rawPassword);

        // Compare the entered hash with the stored hash
        return enteredHash.equals(storedHash);
    }

    //Remove Driver Access
    @SuppressWarnings("null")
    public String DeleteDriver(Driver driver) {

        Driver drivers=driverRepository.checkById(driver.getDriverId());
        if(drivers==null){
            throw new ResourceNotFoundException("Driver Not found");
        }else{
            drivers.setDriverIsActive(false);
            drivers.setDriverDeletedTime(LocalDateTime.now());
            drivers.setRemarks(driver.getRemarks());
            driverRepository.save(drivers);
            System.out.println(drivers);
            if(drivers.getDriverType().equals(env.getProperty("Insidedrivertype"))){
                DrivervehicleAssociation drivervehicleAssociation=driverVehicleAssociationRepository.findDriverVehicleByDriverId(driver.getDriverId());
                driverVehicleAssociationRepository.delete(drivervehicleAssociation);
                return env.getProperty("Insidedrivertype")+" Deleted Successflly";
            }
            return env.getProperty("Outsidedrivertype")+"Deleted successfylly";
        }  
    }

    //Bind Driver Access
    public String BindDriver(Driver driver){
        Driver drivers=driverRepository.checkById(driver.getDriverId());
        if(drivers==null){
            throw new ResourceNotFoundException("Driver Not found");
        }else{
            drivers.setDriverIsActive(true);
            drivers.setDriverDeletedTime(null);
            drivers.setLastUpdatedTime(LocalDateTime.now());
            driverRepository.save(drivers);
            return "Driver Added Successfully Please update vehicle";
        }
    }

    
}
