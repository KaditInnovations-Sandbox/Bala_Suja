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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.travelease.travelease.exception.ResourceNotFoundException;
import com.travelease.travelease.model.hubmodel.Driver;
import com.travelease.travelease.model.hubmodel.DrivervehicleAssociation;
import com.travelease.travelease.model.hubmodel.Vehicle;
import com.travelease.travelease.model.loginmodel.DriverLogin;
import com.travelease.travelease.repository.DriverLoginRepository;
import com.travelease.travelease.repository.DriverRepository;
import com.travelease.travelease.repository.DriverVehicleAssociationRepository;
import com.travelease.travelease.repository.VehicleRepository;
import com.travelease.travelease.util.JwtUtils;

@Service
public class HubService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private DriverLoginRepository driverLoginRepository;
    
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
        Driver isDriver=driverRepository.findByDriverPhone((BigInteger)driverdetails.get("Driverphone"));
        Driver driver=new Driver();
        //sevilai Drivers
        if(driverdetails.get("DriverType").equals(env.getProperty("Insidedrivertype"))&& isDriver==null){
            driver.setDriverName((String)driverdetails.get("DriverName"));
            driver.setDriverPhoneNumber(new BigInteger(String.valueOf(driverdetails.get("DriverPhone"))));
            driver.setDriverEmail((String)driverdetails.get("DriverEmail"));
            driver.setDriverPassword(encodePassword((driverdetails.get("DriverPhone")).toString()));
            driver.setDriverType(env.getProperty("Insidedrivertype"));
            driverRepository.save(driver);
            DrivervehicleAssociation drivervehicleAssociation= new DrivervehicleAssociation();
            drivervehicleAssociation.setDriverId(driver);
            drivervehicleAssociation.setVehicleId(vehicleRepository.findByVehicleNumber((String)driverdetails.get("VehicleNumber")));
            driverVehicleAssociationRepository.save(drivervehicleAssociation);
            return env.getProperty("Insidedrivertype")+" Driver Created";
        }else if(driverdetails.get("DriverType").equals(env.getProperty("Outsidedrivertype"))  && isDriver==null){
            driver.setDriverName((String)driverdetails.get("DriverName"));
            driver.setDriverPhoneNumber(new BigInteger(String.valueOf(driverdetails.get("DriverPhone"))));
            driver.setDriverEmail((String)driverdetails.get("DriverEmail"));
            driver.setDriverPassword(encodePassword((driverdetails.get("DriverPhone")).toString()));
            driver.setDriverType(env.getProperty("Outsidedrivertype"));
            driverRepository.save(driver);
            Vehicle vehicle=vehicleRepository.findByVehicleNumber((String)driverdetails.get("VehicleNumber"));
            Vehicle newVehicle = new Vehicle();
            if(vehicle == null){
                newVehicle.setVehicleCapacity((String)driverdetails.get("VehicleCapacity"));
                newVehicle.setVehicleNumber((String)driverdetails.get("VehicleNumber"));
                newVehicle.setVehicleType(env.getProperty("Outsidedrivertype"));
                vehicleRepository.save(newVehicle);
                DrivervehicleAssociation drivervehicleAssociation= new DrivervehicleAssociation();
                drivervehicleAssociation.setDriverId(driver);
                drivervehicleAssociation.setVehicleId(newVehicle);
                driverVehicleAssociationRepository.save(drivervehicleAssociation);
            }
            return env.getProperty("Outsidedrivertype")+" Driver Created";
        }else{
            System.out.println(driverdetails);
            throw new Exception("Driver already exists");
        }
    }

    //Vehicle data read from csv
    // public Integer saveOutsideDriverFromCsv(MultipartFile file) throws IOException {
    //     List<Driver> drivers = new ArrayList<>();
    //     List<Vehicle> vehicles=new ArrayList<>();
    //     Integer count=0;
    //     try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
    //         String line;
    //         while ((line = reader.readLine()) != null) {
    //             count++;
    //             String[] data = line.split(",");
    //             Driver driver=new Driver();
    //             driver.setDriverName(data[0]);
    //             driver.setDriverPhoneNumber(new BigInteger(data[1]));
    //             driver.setDriverEmail(data[2]);
    //             driver.setDriverPassword(encodePassword(data[1]));
    //             driver.setDriverType(env.getProperty("Outsidedrivertype"));
    //             Vehicle vehicle=vehicleRepository.findByVehicleNumber(data[4]);
    //             Vehicle newVehicle = new Vehicle();
    //             if(vehicle == null){
    //                 newVehicle.setVehicleCapacity(data[3]);
    //                 newVehicle.setVehicleNumber(data[4]);
    //                 newVehicle.setVehicleType(env.getProperty("Outsidedrivertype"));
    //                 vehicles.add(newVehicle);
    //                 DrivervehicleAssociation drivervehicleAssociation= new DrivervehicleAssociation();
    //                 drivervehicleAssociation.setDriverId(driver);
    //                 drivervehicleAssociation.setVehicleId(newVehicle);
    //                 driverVehicleAssociationRepository.save(drivervehicleAssociation);
    //             }
    //             drivers.add(driver);

    //         }
    //     }
    //     vehicleRepository.saveAll(vehicles);
    //     return count;
    // }

    //Update Driver
    public String UpdateDriver(Map<String,Object> driverdetails)throws Exception{
        Driver driver=driverRepository.checkById(Long.valueOf(driverdetails.get("DriverId").toString()));
        DrivervehicleAssociation drivervehicleAssociation=driverVehicleAssociationRepository.findDriverVehicleByDriverId(Long.valueOf(driverdetails.get("DriverId").toString()));
        if(driver!=null){
            driver.setDriverName((String)driverdetails.get("DriverName"));
            driver.setDriverPhoneNumber(new BigInteger(String.valueOf(driverdetails.get("DriverPhone"))));
            driver.setDriverEmail((String)driverdetails.get("DriverEmail"));
            driver.setDriverPassword(encodePassword((driverdetails.get("DriverPhone")).toString()));
            driver.setLastUpdatedTime(LocalDateTime.now());
            driverRepository.save(driver);
            drivervehicleAssociation.setDriverId(driver);
            if(driverdetails.get("DriverType").equals(env.getProperty("Insidedrivertype"))){
                drivervehicleAssociation.setVehicleId(vehicleRepository.findByVehicleNumber((String)driverdetails.get("VehicleNumber")));
                return env.getProperty("Insidedrivertype")+" Driver Updated";
            }else if(driverdetails.get("DriverType").equals(env.getProperty("Outsidedrivertype"))){
                Vehicle vehicle=vehicleRepository.findByVehicleId(drivervehicleAssociation.getVehicleId().getVehicleId());
                vehicle.setVehicleNumber((String)driverdetails.get("VechicleNumber"));
                vehicle.setVehicleCapacity((String)driverdetails.get("VehicleCapacity"));
                vehicleRepository.save(vehicle);
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
    
    //Driver Login
    public Map<String,Object> driverLogin(Map<String,Object> driverLogin) throws Exception{
        Map<String, Object> resultMap = new HashMap<>();
        Driver driver=driverRepository.findByDriverPhone((BigInteger)driverLogin.get("phone"));
        if(driverLogin.get("token")==null && driver!=null && driver.getDriverIsActive()){
                if(verifyPassword((String)driverLogin.get("password"), driver.getDriverPassword())){
                    //token creation and store login table
                    DriverLogin logindriver=new DriverLogin();
                    logindriver.setDriver(driver);
                    logindriver.setTimestamp(LocalDateTime.now());
                    logindriver.setTokenId(jwtUtils.generateJwtDriver(driver));
                    resultMap.put("token", jwtUtils.generateJwtDriver(driver));
                    driverLoginRepository.save(logindriver);
                    driver.setDriverLastLogin(LocalDateTime.now());
                    driverRepository.save(driver);
                    return resultMap; 
                }else{
                    throw new Exception();
                    //Exception Driver is not match
                }
        }else if(driverLogin.get("token")!=null && driver!=null && driver.getDriverIsActive()){
            resultMap.put("email", jwtUtils.verify((String)driverLogin.get("token")));
            driver.setDriverLastLogin(LocalDateTime.now());
            driverRepository.save(driver);
            return resultMap;

        }else{
            throw new Exception();
            //Exception Driver is not active or not found
        }
    }

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
