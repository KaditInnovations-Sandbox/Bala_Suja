package com.travelease.travelease.Service;

import java.math.BigInteger;


import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.travelease.travelease.model.adminmodel.Admin;
import com.travelease.travelease.repository.AdminRepository;
import com.travelease.travelease.service.AdminService;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminService adminService;

    private Admin admin;

    @BeforeEach
    public void setup(){
        admin = Admin.builder()
            .AdminName("Bala")
            .AdminEmail("bala@gmail.com")
            .AdminPhone(new BigInteger("786545673"))
            .AdminRoleType("SUPER_ADMIN")
            .build();

        when(adminRepository.save(Mockito.any(Admin.class))).thenReturn(admin);
    }

    @Test
    public void create_admin_test() throws Exception{
        Admin admin1 = Admin.builder()
                        .AdminName("Bala")
                        .AdminEmail("bala@gmail.com")
                        .AdminPhone(new BigInteger("786545673"))
                        .AdminRoleType("SUPER_ADMIN")
                        .build();

        // Admin admin1 = mock(Admin.class, Mockito.RETURNS_DEEP_STUBS);
        when(adminRepository.save(Mockito.any(Admin.class))).thenReturn(admin);

        String savedString = adminService.createAdmin(admin1);

        Assertions.assertThat(savedString).isEqualTo("Admin Created Sccessfully");
    }

    @Test
    public void get_admin_by_email_test(){

        //Arrange
        String email = "bala@gmail.com";

        //Act
        Admin admin1 = adminService.getAdminByEmail(email);

        //Assert
        Assertions.assertThat(admin1).isEqualTo(admin);

    }

    @Test
    public void bind_admin_test(){

        admin.setAdminIsActive(false);
        
        String result = adminService.bindAdmin(admin);

        Assertions.assertThat(result).isEqualTo("Access Updated");

    }


}
