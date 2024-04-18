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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.travelease.travelease.exception.ResourceNotFoundException;
import com.travelease.travelease.model.companymodel.company;
import com.travelease.travelease.model.passengermodel.passenger;
import com.travelease.travelease.model.routemodel.route;
import com.travelease.travelease.repository.CompanyRepository;
import com.travelease.travelease.repository.PassengerRepository;
import com.travelease.travelease.repository.RouteRepository;
import com.travelease.travelease.repository.StopsRepository;
import com.travelease.travelease.util.JwtUtils;

@Service
public class PassengerService {

    @Autowired
    private JwtUtils jwtUtils;
    
    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private CompanyRepository companyRepository;
    
    @Autowired
    private StopsRepository stopsRepository;

    @Autowired
    private RouteRepository routeRepository;
  
    //get all passenger
    public List<passenger> getAllPassengerDetails(){
        return passengerRepository.findAll();
    }

    //get all active passenger
    public List<passenger> getAllActivePassenger(){
        return passengerRepository.findByAccessTrue();
    }

    //get passenger based on company
    public List<passenger> getCompanyBasedPassenger(String companyname) {
        List<passenger> passengers=passengerRepository.findByCompanyId(companyRepository.findByComapnyName(companyname).getCompanyId());
        // List<Map<String, Object>> passengerwithroute = new ArrayList<>();
        return passengers;
    }

    //get all inactive passenger
    public List<passenger> getAllInactivePassenger(){
        return passengerRepository.findByAccessFalse();
    }

    //create passenger
    public String createPassenger(String companyname, Map<String,Object> passengerDetails) {
        company company=companyRepository.findByComapnyName(companyname);
        if(company!=null && company.getCompanyIsActive()){
            if(passengerRepository.findByPassengerPhone(new BigInteger((String) passengerDetails.get("passenger_phone")))==null){
                passenger passenger=new passenger();
                passenger.setPassengerName((String)passengerDetails.get("passenger_name"));
                passenger.setPassengerEmail((String)passengerDetails.get("passenger_email"));
                passenger.setPassengerPhone(new BigInteger((String) passengerDetails.get("passenger_phone")));
                passenger.setPassengerLocation((String)passengerDetails.get("passenger_location"));
                passenger.setPassengerPassword(encodePassword((new BigInteger((String) passengerDetails.get("passenger_phone"))).toString()));
                passenger.setCompanyId(company);
                passenger.setRouteId(routeRepository.findByRouteId((String)passengerDetails.get("route_id")));
                passengerRepository.save(passenger);
                return "created";
            }else{
                throw new ResourceNotFoundException("Passenger already exists.");
            }
        }else{
            throw new ResourceNotFoundException("Company Not found.");
        }
    }

    //update passenger
    public String updatePassenger(Map<String,Object> passengerDetails)throws Exception{
        passenger passenger=passengerRepository.checkById(Long.valueOf((Integer) passengerDetails.get("passenger_id")));
        if(passenger!=null && passenger.getPassengerIsActive()){
           passenger.setPassengerName((String)passengerDetails.get("passenger_name"));
           passenger.setPassengerEmail((String)passengerDetails.get("passenger_email"));
           passenger.setPassengerPhone(BigInteger.valueOf((Long) passengerDetails.get("passenger_phone")));
           passenger.setPassengerLocation((String)passengerDetails.get("passenger_location"));
           passenger.setRouteId(routeRepository.findByRouteId((String)passengerDetails.get("route_id")));
           passenger.setLastUpdatedTime(LocalDateTime.now());
           passengerRepository.save(passenger);
           return "updated";
        }else{
            throw new ResourceNotFoundException("Passenger not found");
        }
    }

    //passenger Remove access
    public String DeletePassenger(passenger passengers) throws Exception{
        passenger passenger = passengerRepository.findByPassengerPhone(passengers.getPassengerPhone());
        if(passenger != null){
            passenger.setPassengerIsActive(false);
            passenger.setPassengerDeletedTime(LocalDateTime.now());
            passenger.setLastUpdatedTime(LocalDateTime.now());
            passenger.setRemarks(passengers.getRemarks());
            passengerRepository.save(passenger);
            return "Deleted";
        }else{
            throw new ResourceNotFoundException("passenger not found");
        }
    }

    //Bind Passenger
    public String BindPassenger(passenger passengers) {
        passenger passenger = passengerRepository.findByPassengerPhone(passengers.getPassengerPhone());
        if(passenger != null){
            passenger.setPassengerIsActive(true);
            passenger.setPassengerDeletedTime(null);
            passenger.setLastUpdatedTime(LocalDateTime.now());
            passenger.setRemarks(null);
            passengerRepository.save(passenger);
            return "Added";
        }else{
            throw new ResourceNotFoundException("passenger not found");
        }
    }

    //data read from csv
    public void savePassengerFromCsv(MultipartFile file, String companyname) throws IOException {
        if(companyRepository.findByComapnyName(companyname)!=null){
            List<passenger> passengers = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    passenger passenger = new passenger();
                    passenger.setPassengerName(data[0]);
                    passenger.setPassengerEmail(data[1]);
                    passenger.setPassengerPhone(new BigInteger(data[2]));
                    passenger.setPassengerPassword(encodePassword((passenger.getPassengerPhone()).toString()));
                    passenger.setPassengerLocation(data[3]);
                    passenger.setCompanyId(companyRepository.findByComapnyName(companyname));
                    // passenger.setStopId(stopsRepository.findStopIdByStopName(data[5],routeRepository.findByRouteId(data[4]).getId()));
                    passengers.add(passenger);
                }
            }
            passengerRepository.saveAll(passengers);
        }else{
            throw new ResourceNotFoundException("Company Not found.");
        }
        
    }
   
    //Passenger login
    // public Map<String,Object> passengerLogin(Map<String,Object> passengerLogin) throws Exception{
    //     Map<String, Object> resultMap = new HashMap<>();
    //     passenger passenger=passengerRepository.findByPassengerPhone((BigInteger)passengerLogin.get("phone"));
    //     if(passengerLogin.get("token")==null && passenger!=null && passenger.getPassengerIsActive()){
    //             if(verifyPassword((String)passengerLogin.get("password"), passenger.getPassengerPassword())){
    //                 //token creation and store login table
    //                 PassengerLogin loginpassenger=new PassengerLogin();
    //                 loginpassenger.setPassenger(passenger);
    //                 loginpassenger.setTimestamp(LocalDateTime.now());
    //                 loginpassenger.setTokenId(jwtUtils.generateJwtPassenger(passenger));
    //                 resultMap.put("token", jwtUtils.generateJwtPassenger(passenger));
    //                 passengerLoginRepository.save(loginpassenger);
    //                 passenger.setPassengerLastLogin(LocalDateTime.now());
    //                 passengerRepository.save(passenger);
    //                 return resultMap; 
    //             }else{
    //                 System.out.println("Driver is not match");
    //                 throw new Exception();
    //                 //Exception Admin Password Does not match
    //             }
    //     }else if(passengerLogin.get("token")!=null && passenger!=null && passenger.getPassengerIsActive()){
    //         resultMap.put("email", jwtUtils.verify((String)passengerLogin.get("token")));
    //         passenger.setPassengerLastLogin(LocalDateTime.now());
    //         passengerRepository.save(passenger);
    //         return resultMap; 

    //     }else{
    //         System.out.println("Passenger is not active or not found");
    //         throw new Exception();
    //         //Exception Admin is not found
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

    //Route based passenger
    public List<passenger> getRouteBasedPassenger(String routeid) throws Exception{
        route route = routeRepository.findByRouteId(routeid);
        if(route == null){
            throw new ResourceNotFoundException("RouteId not present");
        }else{
            return passengerRepository.findByRouteId(route.getId());
        }
        
    }

    public int getRouteBasedPassengerCount(String routeid) throws Exception{
        int c = passengerRepository.findByRouteIdPassengerCount(routeRepository.findByRouteId(routeid).getId());
        return c;
        
    }

    //Check Passenger by email
    public boolean CheckPassengerByEmail(String email){
        passenger passenger=passengerRepository.findByPassengerEmail(email);
        if(passenger == null){
            return true; // If email is unique then return true
        }else{
            throw new ResourceNotFoundException("Passenger Email already exist");
        }  
    }
       
    //Check Passenger by phone
    public boolean CheckPassengerByPhone(BigInteger phone){
        passenger passenger=passengerRepository.findByPassengerPhone(phone);
        if(passenger == null){
            return true; // If Phone number is unique then return true
        }else{
            throw new ResourceNotFoundException("Passenger Phone Number already exist");
        }  
    }
    
}
