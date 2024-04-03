package com.travelease.travelease.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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

    public List<Admin> getAllAdmin(){
        return adminRepository.findAll();
    }
   
    public Object getAdminByEmail(String email){
        return adminRepository.findByAdminEmail(email);
    }

    public String createAdmin(Admin admin) throws Exception{
        if(adminRepository.findByAdminEmail(admin.getAdminEmail())==null){
            admin.setAdminPassword(encodePassword(admin.getAdminPhone().toString()));          
            adminRepository.save(admin); 
            return "Created";
        }else{  
            throw new ResourceNotFoundException("User Aleredy Exist");  
        }
    }

    //Grand Access 
    public String bindAdmin(Admin adminDetails){
        Admin admin=adminRepository.findByAdminEmail(adminDetails.getAdminEmail());
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

    public String updateAdmin(Admin adminDetails)throws Exception{
        Admin admin=adminRepository.findByAdminEmail(adminDetails.getAdminEmail());
        if(admin==null){
            throw new ResourceNotFoundException("Admin not found");
        }else{
            admin.setAdminName(adminDetails.getAdminName());
            admin.setAdminEmail(adminDetails.getAdminEmail());
            admin.setAdminPhone(adminDetails.getAdminPhone());
            admin.setAdminPassword(encodePassword(adminDetails.getAdminPhone().toString()));
            admin.setAdminRoleType(adminDetails.getAdminRoleType());
            // admin.setAdminDateRegistered(LocalDateTime.now());
            adminRepository.save(admin);
            return "Updated";
        }	
    }

    public String deleteAdmin(Admin admins) throws Exception{
        Admin admin=adminRepository.findByAdminEmail(admins.getAdminEmail());
        if(admin==null){
            throw new ResourceNotFoundException("Admin not found");
        }else{
            admin.setAdminIsActive(false);
            admin.setAdminDeletedTime(LocalDateTime.now());
            admin.setRemarks(admin.getRemarks());
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
                    throw new ResourceNotFoundException("Admin not found");
                    //Exception Admin is not match
                }
        }else if(adminLogin.get("token")!=null && admin!=null && admin.getAdminIsActive()){
            resultMap.put("email", jwtUtils.verify(adminLogin.get("token")));
            admin.setAdminLastLogin(LocalDateTime.now());
            adminRepository.save(admin);
            return resultMap;

        }else{
            throw new ResourceNotFoundException("Admin not Active");
            //Exception Admin is not found or not active
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

       
}



