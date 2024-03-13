package com.travelease.travelease.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travelease.travelease.model.apikeymodel.ApiKey;
import com.travelease.travelease.service.ApikeyService;

@RestController
@RequestMapping("/api/keys")
public class ApikeyController {
    
    @Autowired
    private ApikeyService apiKeyService;
    
    @PostMapping
    public ApiKey generateApiKeyForRole(String role) {
        // Generate and store API key for the specified role
        return apiKeyService.generateAndStoreApiKey(role);
    }
}

