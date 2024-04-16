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
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.travelease.travelease.repository.AdminLoginRepository;
import com.travelease.travelease.repository.AdminRepository;
import com.travelease.travelease.util.JwtUtils;

import com.travelease.travelease.exception.ResourceNotFoundException;
import com.travelease.travelease.model.adminmodel.Admin;
import com.travelease.travelease.model.loginmodel.AdminLogin;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AdminLoginRepository adminLoginRepository;
    
    @Autowired
    private JwtUtils jwtUtils;

    //get all admin
    public List<Admin> getAllAdmin(){
        return adminRepository.findAll();
    }
   
    //get admin by email
    public Admin getAdminByEmail(String email){
        Optional<Admin> adminOptional = Optional.ofNullable(adminRepository.findByAdminEmail(email));
        return adminOptional.orElse(null); 
    }

    //post admin
    public String createAdmin(Admin admin) throws Exception{
        if(adminRepository.findByAdminEmail(admin.getAdminEmail())==null){
            admin.setAdminPassword(encodePassword(admin.getAdminPhone().toString()));           
            adminRepository.save(admin); 
            return "Admin Created Sccessfully";
        }else{  
            throw new ResourceNotFoundException("User Aleredy Exist");  
        }
    }

    //Grand Access 
    public String bindAdmin(Admin adminDetails){
        Admin admin=adminRepository.checkById(adminDetails.getAdminId());
        if(admin==null){
            throw new ResourceNotFoundException("Admin Not found");
        }else{
            admin.setAdminDeletedTime(null);
            admin.setAdminIsActive(true);
            admin.setAdminLastUpdatedTime(LocalDateTime.now());
            admin.setRemarks(null);
            adminRepository.save(admin);
            return "Access Updated";
        }       
    }

    //Update Admin
    public String updateAdmin(Admin adminDetails)throws Exception{
        Admin admin=adminRepository.checkById(adminDetails.getAdminId());
        if(admin==null){
            throw new ResourceNotFoundException("Admin not found");
        }else{
            admin.setAdminName(adminDetails.getAdminName());
            admin.setAdminEmail(adminDetails.getAdminEmail());
            admin.setAdminPhone(adminDetails.getAdminPhone());
            admin.setAdminPassword(encodePassword(adminDetails.getAdminPhone().toString()));
            admin.setAdminRoleType(adminDetails.getAdminRoleType());
            adminRepository.save(admin);
            return "Updated";
        }	
    }

    //Delete admin
    public String deleteAdmin(Admin admins) throws Exception{
        Admin admin=adminRepository.checkById(admins.getAdminId());
        if(admin==null){
            throw new ResourceNotFoundException("Admin not found");
        }else{
            admin.setAdminIsActive(false);
            admin.setAdminDeletedTime(LocalDateTime.now());
            admin.setRemarks(admins.getRemarks());
            System.out.println(admin);
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
                    loginuser.setAdminId(admin);
                    loginuser.setLoginTime(LocalDateTime.now());
                    loginuser.setTokenId(jwtUtils.generateJwtAdmin(admin));
                    resultMap.put("token", jwtUtils.generateJwtAdmin(admin));
                    resultMap.put("role",admin.getAdminRoleType());
                    adminLoginRepository.save(loginuser);
                    admin.setAdminLastLogin(LocalDateTime.now());
                    adminRepository.save(admin);
                    return resultMap; 
                }else{
                    throw new ResourceNotFoundException("Password Incorrect");
                }
        }else if(adminLogin.get("token")!=null && admin!=null && admin.getAdminIsActive()){
            resultMap.put("email", jwtUtils.verify(adminLogin.get("token")));
            admin.setAdminLastLogin(LocalDateTime.now());
            adminRepository.save(admin);
            return resultMap;

        }else{
            throw new ResourceNotFoundException("Admin not Active");
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
    public String PasswordChange(String email, String password) throws Exception {
        try{
            Admin admin=adminRepository.findByAdminEmail(email); 
            admin.setAdminPassword(encodePassword(password));
            adminRepository.save(admin);
            return "Updated";
        }catch(Exception e){
            throw new Exception(e);
        }
    } 

    //get all active vehicle
    public List<Admin> getAllActiveAdmin(){
        return adminRepository.findByAccessTrue();
    }

    //get all inactive vehicle
    public List<Admin> getAllInactiveAdmin(){
        return adminRepository.findByAccessFalse();
    }

    //data read from csv
    public Integer saveAdminFromCsv(MultipartFile file) throws IOException {
        List<Admin> admins = new ArrayList<>();
        Integer count=0;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Admin admin=new Admin();
                count++;
                if(adminRepository.findByAdminPhone(new BigInteger(data[2]))==null){
                    admin.setAdminName(data[0]);
                    admin.setAdminEmail(data[1]);
                    admin.setAdminPhone(new BigInteger(data[2]));
                    admin.setAdminRoleType(data[3]);
                    admin.setAdminPassword(encodePassword(data[2]));
                    admins.add(admin);
                }else{
                    throw new ResourceNotFoundException("Line Number "+count+" Phone Number already exist");
                }          

            }
        }
        adminRepository.saveAll(admins);
        return count;
    }

    //get admin by type
    public List<Admin> getAdminByType(String type) {
        return adminRepository.findByType(type);
    }

    //get active admin by type
    public List<Admin> getActiveAdminByType(String type){
        return adminRepository.findActiveAdminByType(type);
    }

    //get Inactive admin by type
    public List<Admin> getInactiveAdminByType(String type){
        return adminRepository.findInactiveAdminByType(type);
    }

    //Check Admin by email
    public boolean CheckAdminByEmail(String email){
        Admin admin = adminRepository.findByAdminEmail(email);
        if(admin == null){
            return true; // If email is unique then return true
        }else{
            throw new ResourceNotFoundException("Admin Email already exist");
        }  
    }
       
    //Check Admin by phone
    public boolean CheckAdminByPhone(BigInteger phone){
        Admin admin = adminRepository.findByAdminPhone(phone);
        if(admin == null){
            return true; // If Phone number is unique then return true
        }else{
            throw new ResourceNotFoundException("Admin Phone already exist");
        }  
    }
}



