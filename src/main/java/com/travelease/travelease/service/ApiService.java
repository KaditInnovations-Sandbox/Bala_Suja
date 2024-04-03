package com.travelease.travelease.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travelease.travelease.exception.ResourceNotFoundException;
import com.travelease.travelease.repository.AdminRepository;
import com.travelease.travelease.util.JwtUtils;

@Service
public class ApiService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AdminRepository adminRepository;
    
    public String AdminToken(String token) throws Exception {
        String email = jwtUtils.verify(token);
        if("true".equals(adminRepository.checkAdminExistence(email.substring(0, email.indexOf(","))))){
            return email.substring(email.indexOf(",")+1)+" is Present";
        }else{
            throw new ResourceNotFoundException("Invalid User");
        }
    }


    
}
