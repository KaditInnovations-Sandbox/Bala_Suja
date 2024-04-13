package com.travelease.travelease;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.travelease.travelease.controller.LoginController;
import com.travelease.travelease.repository.AdminRepository;
import com.travelease.travelease.service.AdminService;

public class LoginControllerTest {
    
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private AdminService adminService;

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private LoginController loginController;

    @Test
    public void AdminLogin_Success() throws Exception{
        
        
    }





}
