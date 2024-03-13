package com.travelease.travelease.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travelease.travelease.model.apikeymodel.ApiKey;
import com.travelease.travelease.repository.ApikeyRepository;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class ApikeyService {

    @Autowired
    private ApikeyRepository apikeyRepository;

    public ApiKey generateAndStoreApiKey(String role) {
        String apiKey = generateApiKey();
        String secret = generateSecret();
        
        ApiKey apiKeyEntity = new ApiKey();
        apiKeyEntity.setApiKey(apiKey);
        apiKeyEntity.setSecret(secret);
        apiKeyEntity.setRole(role);
        
        return apikeyRepository.save(apiKeyEntity);
    }
    
    
    // Method to generate a random API key
    public static String generateApiKey() {
        // Generate a random 32-byte array
        byte[] apiKeyBytes = new byte[32];
        new SecureRandom().nextBytes(apiKeyBytes);

        // Encode the byte array to a Base64 string
        return Base64.getEncoder().encodeToString(apiKeyBytes);
    }

    // Method to generate a random secret
    public static String generateSecret() {
        // Generate a random 64-byte array
        byte[] secretBytes = new byte[64];
        new SecureRandom().nextBytes(secretBytes);

        // Encode the byte array to a Base64 string
        return Base64.getEncoder().encodeToString(secretBytes);
    }

    // public String getRoleForApiKey(String Apikey){

    // }

}

