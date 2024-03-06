package com.travelease.travelease.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travelease.travelease.repository.AdminLoginRepository;
import com.travelease.travelease.repository.AdminRepository;
import com.travelease.travelease.repository.RoleRepository;
import com.travelease.travelease.repository.AdminRoleAssociationRepository;
import com.travelease.travelease.repository.DriverLoginRepository;
import com.travelease.travelease.repository.DriverRepository;
import com.travelease.travelease.repository.PassengerLoginRepository;
import com.travelease.travelease.repository.PassengerRepository;
import com.travelease.travelease.util.JwtUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import com.travelease.travelease.exception.ResourceNotFoundException;
import com.travelease.travelease.model.adminmodel.Admin;
import com.travelease.travelease.model.adminmodel.Role;
import com.travelease.travelease.model.hubmodel.Driver;
import com.travelease.travelease.model.adminmodel.AdminRoleAssociation;
import com.travelease.travelease.model.loginmodel.AdminLogin;
import com.travelease.travelease.model.loginmodel.DriverLogin;
import com.travelease.travelease.model.loginmodel.PassengerLogin;
import com.travelease.travelease.model.passengermodel.Passenger;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private RoleRepository roleRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private AdminRoleAssociationRepository adminRoleAssociationRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AdminLoginRepository adminLoginRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private DriverLoginRepository driverLoginRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private PassengerLoginRepository passengerLoginRepository;

    public List<Admin> getAllAdmin(){
        return adminRepository.findAll();
    }
    
    public Object getAdminByEmail(String email){
        Admin admin=adminRepository.findByAdminEmail(email);
        return admin;
    }

    @Transactional
    public String createAdmin(Admin admin,String roleName) throws Exception{
        String newEmail=admin.getAdminEmail();
        String result = adminRepository.checkAdminExistence((String)newEmail);
        if("1".equals(result)){
            throw new ResourceNotFoundException("User Aleredy Exist");
        }else{  
            admin.setAdminPassword(encodePassword(admin.getAdminPassword()));          
            // admin.setAdminDateRegistered(LocalDateTime.now());
            admin.setAdminIsActive(true);
            Admin adminObject=adminRepository.save(admin);
            Role roleObject=roleRepository.findByAdminRoleName(roleName);
            AdminRoleAssociation URAssociation=new AdminRoleAssociation();
            URAssociation.setUser(adminObject);
            URAssociation.setRole(roleObject);
            entityManager.persist(URAssociation);
            return "Created";
        }
    }

    public String updateAdmin(Admin adminDetails)throws Exception{
        Admin admin=adminRepository.findByAdminEmail(adminDetails.getAdminEmail());
        if(admin==null){
            throw new ResourceNotFoundException("Admin not found");
        }else{
            admin.setAdminName(adminDetails.getAdminName());
            admin.setAdminEmail(adminDetails.getAdminEmail());
            admin.setAdminPhone(adminDetails.getAdminPhone());;
            admin.setAdminName(adminDetails.getAdminName());
            // admin.setAdminDateRegistered(LocalDateTime.now());
            adminRepository.save(admin);
            return "Updated";
        }	
    }

    public String deleteAdmin(String email) throws Exception{
        Admin admin=adminRepository.findByAdminEmail(email);
        if(admin==null){
            throw new ResourceNotFoundException("Admin not found");
        }else{
            admin.setAdminIsActive(false);
            adminRepository.save(admin);
            return "Deleted";
        }
    }

    //Admin Login
    public Map<String,String> adminLogin(Map<String,String> adminLogin) throws Exception{
        Map<String, String> resultMap = new HashMap<>();
        Admin admin=adminRepository.findByAdminEmail(adminLogin.get("email"));
        if(adminLogin.get("token")==null && admin!=null && admin.getAdminIsActive()){
                if(verifyPassword(adminLogin.get("password"), admin.getAdminPassword())){
                    //token creation and store login table
                    AdminLogin loginuser=new AdminLogin();
                    loginuser.setAdmin(admin);
                    loginuser.setTimestamp(LocalDateTime.now());
                    loginuser.setTokenId(jwtUtils.generateJwtAdmin(admin));
                    resultMap.put("token", jwtUtils.generateJwtAdmin(admin));
                    resultMap.put("role",adminRoleAssociationRepository.findByAdmin(admin.getAdminId()).getRole().getAdminRoleName());
                    adminLoginRepository.save(loginuser);
                    admin.setAdminLastLogin(LocalDateTime.now());
                    adminRepository.save(admin);
                    return resultMap; 
                }else{
                    throw new Exception();
                    //Exception Admin is not match
                }
        }else if(adminLogin.get("token")!=null && admin!=null && admin.getAdminIsActive()){
            resultMap.put("email", jwtUtils.verify(adminLogin.get("token")));
            admin.setAdminLastLogin(LocalDateTime.now());
            adminRepository.save(admin);
            return resultMap;

        }else{
            throw new Exception();
            //Exception Admin is not found or not active
        }
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
   
    //Passenger login
    public Map<String,Object> passengerLogin(Map<String,Object> passengerLogin) throws Exception{
        Map<String, Object> resultMap = new HashMap<>();
        Passenger passenger=passengerRepository.findByPassengerPhone((BigInteger)passengerLogin.get("phone"));
        if(passengerLogin.get("token")==null && passenger!=null && passenger.getPassengerIsActive()){
                if(verifyPassword((String)passengerLogin.get("password"), passenger.getPassengerPassword())){
                    //token creation and store login table
                    PassengerLogin loginpassenger=new PassengerLogin();
                    loginpassenger.setPassenger(passenger);
                    loginpassenger.setTimestamp(LocalDateTime.now());
                    loginpassenger.setTokenId(jwtUtils.generateJwtPassenger(passenger));
                    resultMap.put("token", jwtUtils.generateJwtPassenger(passenger));
                    passengerLoginRepository.save(loginpassenger);
                    passenger.setPassengerLastLogin(LocalDateTime.now());
                    passengerRepository.save(passenger);
                    return resultMap; 
                }else{
                    System.out.println("Driver is not match");
                    throw new Exception();
                    //Exception Admin Password Does not match
                }
        }else if(passengerLogin.get("token")!=null && passenger!=null && passenger.getPassengerIsActive()){
            resultMap.put("email", jwtUtils.verify((String)passengerLogin.get("token")));
            passenger.setPassengerLastLogin(LocalDateTime.now());
            passengerRepository.save(passenger);
            return resultMap; 

        }else{
            System.out.println("Passenger is not active or not found");
            throw new Exception();
            //Exception Admin is not found
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

    //Update password
    @Transactional
    public String PasswordChange(String email, String password) throws Exception {
        try{
            Admin admin=adminRepository.findByAdminEmail(email); 
            admin.setAdminPassword(encodePassword(password));
            entityManager.persist(admin);
            return "Updated";
        }catch(Exception e){
            throw new Exception(e);
        }
    }
 
}



