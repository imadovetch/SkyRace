package com.PigeonSkyRace.Auth.Controller;

import com.PigeonSkyRace.Auth.Entity.User.Breeder;
import com.PigeonSkyRace.Auth.Repository.BreederRepository;
import com.PigeonSkyRace.Auth.Repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.management.relation.Role;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BreederRepository breederRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void assignRole_AdminAccess_Success() throws Exception {

        Breeder mockUser = new Breeder();
        mockUser.setId(1L);
        when(breederRepository.findById(1L)).thenReturn(Optional.of(mockUser));


        mockMvc.perform(MockMvcRequestBuilders.post("/Admin/AssignRole/1")
                        .param("roleName", "USER"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void assignRole_UserAccess_Forbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/Admin/AssignRole/1")
                        .param("roleName", "USER"))
                .andExpect(status().isForbidden());
    }
}

