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
import com.travelease.travelease.util.JwtUtils;

@Service
public class PassengerService {

    @Autowired
    private JwtUtils jwtUtils;
    
    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private CompanyRepository companyRepository;
  
    //get all passenger
    public List<passenger> getAllPassengerDetails(){
        return passengerRepository.findAll();
    }

    //get all active passenger
    public List<passenger> getAllActivePassenger(){
        return passengerRepository.findByAccessTrue();
    }

    //get passenger based on company
    // public List<passenger> getCompanyBasedPassenger(Long companyid) {
        // List<companyPassengerAssocaiation> companypassengers=companyPassengerRepository.findPassengerByCompany(companyid);
        // List<passenger> passengers=new ArrayList<>();
        // for (companyPassengerAssocaiation item : companypassengers) {
        //     passengers.add(item.getPassengerId());
        // }
        // return passengers;
    // }

    //get all inactive passenger
    public List<passenger> getAllInactivePassenger(){
        return passengerRepository.findByAccessFalse();
    }

    //create passenger
    public String createPassenger(String companyname, Map<String,Object> passengerDetails) {
        if(companyRepository.findByComapnyName(companyname)!=null){
            // if(passengerRepository.findByPassengerPhone(passengerDetails.get())==null){
                // passenger.setPassengerPassword(encodePassword((passenger.getPassengerPhone()).toString()));
                


                //Route and company must be added 
                // passenger_id;
                //  passenger_name;
                //  passenger_email;
                //  passenger-phone;
                //  passenger_location;
                //  passenger_password;
                //  remarks;
                //  route_id;







                // passengerRepository.save(passenger);
                return "created";
            // }else{
            //     throw new ResourceNotFoundException("Passenger already exists.");
            // }
        }else{
            throw new ResourceNotFoundException("Company Not found.");
        }
    }

    //update passenger
    @SuppressWarnings("null")
    public String updatePassenger(passenger passengerDetails)throws Exception{
        passenger passenger=passengerRepository.checkById(passengerDetails.getPassengerId());
        if(passengerRepository.findById(passengerDetails.getPassengerId()).isPresent() && passenger.getPassengerIsActive()){
           passenger.setPassengerName(passengerDetails.getPassengerName());
           passenger.setPassengerEmail(passengerDetails.getPassengerEmail());
           passenger.setPassengerPhone(passengerDetails.getPassengerPhone());
           passenger.setPassengerLocation(passengerDetails.getPassengerLocation());
           passenger.setPassengerPassword(encodePassword((passenger.getPassengerPhone()).toString()));
           passenger.setLastUpdatedTime(LocalDateTime.now());
           passengerRepository.save(passenger);
           return "updated";
        }else{
            throw new Exception();
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
                    passengers.add(passenger);
                }
            }
            passengerRepository.saveAll(passengers);
            for (passenger item : passengers) {
               


                //Route and company must be added 





            }
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

    
}
