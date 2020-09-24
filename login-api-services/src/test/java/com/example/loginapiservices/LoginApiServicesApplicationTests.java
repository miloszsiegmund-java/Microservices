package com.example.loginapiservices;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LoginApiServicesApplicationTests {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void contextLoads() {
    }

    @Test
    public void unsuccessfulAuthenticationWithWrongPassword() throws Exception {
        mockMvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"password\": \"wrong\", \"username\": \"user\"}"))
                .andExpect(status().isUnauthorized());
    }

}
