package com.travelease.travelease.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travelease.travelease.repository.AdminLoginRepository;
import com.travelease.travelease.repository.AdminRepository;
import com.travelease.travelease.repository.RoleRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import com.travelease.travelease.exception.ResourceNotFoundException;
import com.travelease.travelease.model.adminmodel.Admin;
import com.travelease.travelease.model.adminmodel.Role;
import com.travelease.travelease.model.adminmodel.UserRoleAssociation;
import com.travelease.travelease.model.loginmodel.AdminLogin;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private RoleRepository roleRepository;

    @PersistenceContext
    private EntityManager entityManager;

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
            admin.setAdminDateRegistered(LocalDateTime.now());
            admin.setAdminIsActive(true);
            Admin adminObject=adminRepository.save(admin);
            Role roleObject=roleRepository.findByAdminRoleName(roleName);
            UserRoleAssociation URAssociation=new UserRoleAssociation();
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
            admin.setAdminFirstName(adminDetails.getAdminFirstName());
            admin.setAdminLastName(adminDetails.getAdminLastName());
            admin.setAdminDateRegistered(LocalDateTime.now());
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

    @Autowired
    private AdminLoginRepository adminLoginRepository;
    //Admin Login
    @Transactional
    public String adminLogin(AdminLogin newLogin,String key) throws Exception{
        Admin admin=new Admin();
        AdminLogin loginuser=new AdminLogin();
        if("phone".equals(key)){
            admin=adminRepository.findByAdminPhone((BigInteger)newLogin.getAdminPhone());
            loginuser=adminLoginRepository.findByAdminPhone((BigInteger)newLogin.getAdminPhone());
        }else if("email".equals(key)){
            admin=adminRepository.findByAdminEmail((String)newLogin.getAdminEmail());
            loginuser=adminLoginRepository.findByAdminEmail((String)newLogin.getAdminEmail());
        }       
        if(loginuser!=null ){
            if(verifyPassword(newLogin.getAdminPassword(), loginuser.getAdminPassword())){ 
                loginuser.setTimestamp(LocalDateTime.now());
                admin.setAdminLastLogin(LocalDateTime.now());
                return admin.getAdminFirstName();
            }else{
                throw new Exception("Password does not match");
            }

        }else if(loginuser==null && admin!=null){
            if(verifyPassword(newLogin.getAdminPassword(), admin.getAdminPassword())){
                newLogin.setAdminPassword(encodePassword(newLogin.getAdminPassword()));
                newLogin.setTimestamp(LocalDateTime.now());
                admin.setAdminLastLogin(LocalDateTime.now());
                entityManager.persist(newLogin);
                return admin.getAdminFirstName();
            }else{
                throw new Exception("Password does not match");
            }
        }
        return null;     
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
