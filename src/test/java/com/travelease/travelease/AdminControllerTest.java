package com.travelease.travelease;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigInteger;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelease.travelease.controller.AdminController;
import com.travelease.travelease.model.adminmodel.Admin;
import com.travelease.travelease.repository.AdminRepository;
import com.travelease.travelease.service.AdminService;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
public class AdminControllerTest {


    // @MockBean
    // AdminService adminService;

    // ObjectMapper mapper = new ObjectMapper();

    // @Autowired
    // private MockMvc mockMvc;

    // @Test
    // public void create_admin_test() throws Exception{
    //     Admin admin = new Admin();
    //     admin.setAdminName("bala");
    //     admin.setAdminEmail("bala03@gmail.com");
    //     admin.setAdminPhone(new BigInteger("1234567890"));
    //     admin.setAdminRoleType("SUPER_ADMIN");

    //     when(adminService.createAdmin(any(Admin.class))).thenReturn();
    // }



      //JUnit test for saveAdmin
    //   @Test()
    //   public void saveAdmin(){
    //     Admin admin = Admin.builder()
    //                     .AdminName("BalaSuja")
    //                     .AdminEmail("sujabala114@gmail.com")
    //                     .AdminPhone(new BigInteger("2345678901"))
    //                     .AdminRoleType("SUPER_ADMIN")
    //                     .build();

    //     adminRepository.save(admin);

    //     // Mocking repository behavior to save and return Admin
    //     when(adminRepository.save(any(Admin.class))).thenReturn(admin);
    //     when(adminRepository.findById(10001l)).thenReturn(Optional.of(admin));



    //     Assertions.assertThat(admin.getAdminId()).isGreaterThan(0);

    //   }

    //   @Autowired
    //   private AdminService adminService;
  
      @MockBean
      private AdminRepository adminRepository;
  
    //   @Test
    //   public void GetAdminByEmailTest() {
    //       Admin admin = Admin.builder()
    //                           .AdminId(10001l)
    //                           .AdminName("suja")
    //                           .AdminEmail("sujabala114@gmail.com")
    //                           .AdminPhone(new BigInteger("1234567890"))
    //                           .build();
  
    //       // Mocking repository behavior to return Optional<Admin>
    //       when(adminRepository.findByAdminEmail(anyString())).thenReturn(Optional.of(admin));
  
    //       String email = "sujabala114@gmail.com";
    //       Admin adminbyemail = adminService.getAdminByEmail(email);
  
    //       // Check for null condition
    //       assertNotNull(adminbyemail);
          
    //       System.out.println(admin + "*" + adminbyemail);
    //       assertEquals(admin.getAdminId(), adminbyemail.getAdminId());
    //   }
      

}
