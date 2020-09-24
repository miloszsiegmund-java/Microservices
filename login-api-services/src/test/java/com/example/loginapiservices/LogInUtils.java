package com.example.loginapiservices;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public final class LogInUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private LogInUtils() {
    }

    public static String getTokenForLogin(String username, String password, MockMvc mockMvc) throws Exception {
        String content = mockMvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"password\": \"" + password + "\", \"username\": \"" + username + "\"}"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        AuthenticationResponse authResponse = OBJECT_MAPPER.readValue(content, AuthenticationResponse.class);

        return authResponse.getToken();
    }

    private static class AuthenticationResponse {

        private String token;

        public void setToken(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }
    }
}
