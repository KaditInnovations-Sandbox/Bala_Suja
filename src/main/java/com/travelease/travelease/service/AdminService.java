package com.travelease.travelease.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travelease.travelease.repository.AdminLoginRepository;
import com.travelease.travelease.repository.AdminRepository;
import com.travelease.travelease.repository.RoleRepository;
import com.travelease.travelease.repository.AdminRoleAssociationRepository;
import com.travelease.travelease.util.JwtUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import com.travelease.travelease.exception.ResourceNotFoundException;
import com.travelease.travelease.model.adminmodel.Admin;
import com.travelease.travelease.model.adminmodel.Role;
import com.travelease.travelease.model.adminmodel.AdminRoleAssociation;
import com.travelease.travelease.model.loginmodel.AdminLogin;

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

    //Admin Login via Email
    // @Transactional
    // public String adminEmailLogin(AdminLogin adminLogin) throws Exception{
    //     Admin admin=adminRepository.findByAdminEmail(adminLogin.getAdminEmail());
    //     AdminLogin loginuser=adminLoginRepository.findByAdminEmail(adminLogin.getAdminEmail());
    //     AdminRoleAssociation userRole=adminRoleAssociationRepository.findByAdmin(admin.getAdminId()); 
    //     if(loginuser!=null ){
    //         if(verifyPassword(adminLogin.getAdminPassword(), loginuser.getAdminPassword())){ 
    //             loginuser.setTimestamp(LocalDateTime.now());
    //             admin.setAdminLastLogin(LocalDateTime.now());
    //             return admin.getAdminName();
    //         }else{
    //             throw new Exception("Password does not match");
    //         }
    //     }else if(loginuser==null && admin!=null){
    //         if(verifyPassword(adminLogin.getAdminPassword(), admin.getAdminPassword())){
    //             adminLogin.setAdminPassword(encodePassword(adminLogin.getAdminPassword()));
    //             adminLogin.setTimestamp(LocalDateTime.now());
    //             admin.setAdminLastLogin(LocalDateTime.now());
    //             adminLogin.setRole(userRole.getRole());
    //             String token=jwtUtils.generateJwt(admin);
    //             entityManager.persist(adminLogin);
    //             String ans=admin.getAdminName()+" , "+token;
    //             return ans;
    //         }else{
    //             throw new Exception("Password does not match");
    //         }
    //     }else{
    //         throw new Exception("User not found");
    //     }   
    // }

    //Admin Login
    public Map<String,String> adminLogin(Map<String,String> adminLogin) throws Exception{
        if(adminLogin.get("token")==null && adminRepository.findByAdminEmail(adminLogin.get("email"))!=null){
            Admin admin=adminRepository.findByAdminEmail(adminLogin.get("email"));
            if(admin.getAdminIsActive()){
                if(verifyPassword(admin.getAdminPassword(), adminLogin.get("password"))){
                    //token creation and store login table
                    AdminLogin loginuser=new AdminLogin();
                    Map<String, String> resultMap = new HashMap<>();
                    loginuser.setAdmin(admin);
                    loginuser.setTimestamp(LocalDateTime.now());
                    loginuser.setTokenId(jwtUtils.generateJwtAdmin(admin));
                    resultMap.put("token", jwtUtils.generateJwtAdmin(admin));
                    resultMap.put("role",adminRoleAssociationRepository.findRoleByAdmin(admin.getAdminId()).getAdminRoleName());
                    adminLoginRepository.save(loginuser);
                    return resultMap; 
                }else{
                    //Exception Admin Password Does not match
                }
            }else{
               //Exception Admin is not active
            }
        }else if(adminLogin.get("token")!=null && adminRepository.findByAdminEmail(adminLogin.get("email"))!=null){
            if(jwtUtils.verify(adminLogin.get("token"))){
                
            }
        }else{
            //Exception Admin is not found
        }
        return null;
    }

    //Admin Login via Phone
    // @Transactional
    // public String adminPhoneLogin(AdminLogin adminLogin) throws Exception{
    //     Admin admin=adminRepository.findByAdminPhone(adminLogin.getAdminPhone());
    //     AdminLogin loginuser=adminLoginRepository.findByAdminPhone(adminLogin.getAdminPhone());
    //     AdminRoleAssociation userRole=adminRoleAssociationRepository.findByAdmin(admin.getAdminId()); 
    //     if(loginuser!=null ){
    //         if(verifyPassword(adminLogin.getAdminPassword(), loginuser.getAdminPassword())){ 
    //             loginuser.setTimestamp(LocalDateTime.now());
    //             admin.setAdminLastLogin(LocalDateTime.now());
    //             return admin.getAdminName();
    //         }else{
    //             throw new Exception("Password does not match");
    //         }
    //     }else if(loginuser==null && admin!=null){
    //         if(verifyPassword(adminLogin.getAdminPassword(), admin.getAdminPassword())){
    //             adminLogin.setAdminPassword(encodePassword(adminLogin.getAdminPassword()));
    //             adminLogin.setTimestamp(LocalDateTime.now());
    //             admin.setAdminLastLogin(LocalDateTime.now());
    //             adminLogin.setRole(userRole.getRole());
    //             entityManager.persist(adminLogin);
    //             return admin.getAdminName();
    //         }else{
    //             throw new Exception("Password does not match");
    //         }
    //     }else{
    //         throw new Exception("User not found");
    //     }   
    // }

    //get created Admin through Admin Dto
    // public List<?> getAllAssignedUser(Admin admin){

    //     return adminRepository.findAll()
    //         .stream()
    //         .map(this::convertEntityToDto)
    //         .collect(Collectors.toList());
    // }

    // private AdminDriverDto convertEntityToDto(Admin admin){
    //     AdminDriverDto admindriverDto=new AdminDriverDto();
    //     admindriverDto.setAdminName(admin.getAdminName());
    //     admindriverDto.setAdminEmail(admin.getAdminEmail());
    //     admindriverDto.setAdminPhone(admin.getAdminPhone());
    //     Role role=adminRoleAssociationRepository.findRoleByAdmin(admin.getAdminId());
    //     admindriverDto.setRole(role.getAdminRoleName());
    //     // userdriverDto.setDriverName(user.getDriver().getDriverName());
    //     // userdriverDto.setLatitude(user.getDriver().getLatitude());
    //     // userdriverDto.setLongitude(user.getDriver().getLongitude());

    //     // userdriverDto = modelMapper.map(user,UserDriverDto.class);
    //     return admindriverDto;
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



