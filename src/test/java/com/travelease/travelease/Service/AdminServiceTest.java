package com.travelease.travelease.Service;

import java.math.BigInteger;

import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.travelease.travelease.model.adminmodel.Admin;
import com.travelease.travelease.repository.AdminRepository;
import com.travelease.travelease.service.AdminService;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminService adminService;


    @Test
    public void create_admin_test() throws Exception{
        Admin admin = Admin.builder()
                        .AdminName("Bala")
                        .AdminEmail("bala@gmail.com")
                        .AdminPhone(new BigInteger("786545673"))
                        .AdminRoleType("SUPER_ADMIN")
                        .build();

        Admin admin1 = mock(Admin.class, Mockito.RETURNS_DEEP_STUBS);
        when(adminRepository.save(Mockito.any(Admin.class))).thenReturn(admin1);

        String savedString = adminService.createAdmin(admin1);

        Assertions.assertThat(savedString).isEqualTo("Admin Created Sccessfully");
    }

}
