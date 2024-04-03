package com.travelease.travelease.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.travelease.travelease.service.ApiService;
import com.travelease.travelease.util.JwtUtils;

@CrossOrigin(origins = "${crossorigin}")
@RestController
public class ApiController {

    @Value("${crossorigin}")
	private String crossorigin;

    @Autowired
    private ApiService apiService;

    @PostMapping("/TokenValidation")
	public ResponseEntity<String> AdminToken(@RequestBody String token) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(apiService.AdminToken(token));   
	}


    
}
