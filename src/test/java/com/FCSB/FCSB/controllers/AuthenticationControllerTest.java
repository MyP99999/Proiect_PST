package com.FCSB.FCSB.controllers;

import com.FCSB.FCSB.auth.AuthenticationRequest;
import com.FCSB.FCSB.auth.AuthenticationResponse;
import com.FCSB.FCSB.jwt.JwtService;
import com.FCSB.FCSB.responses.MessageResponse;
import com.FCSB.FCSB.services.AuthenticationService;
import com.FCSB.FCSB.validations.RegisterValidation;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private JwtService jwtService;

    @MockBean
    private RegisterValidation registerValidation;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void authenticate_withValidCredentials_shouldReturnToken() throws Exception {
        AuthenticationRequest request = new AuthenticationRequest("ion.popescu@example.com", "Parola123!");
        AuthenticationResponse response = new AuthenticationResponse("jwt-token", null);

        Mockito.when(authenticationService.authenticate(any(AuthenticationRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt-token"))
                .andExpect(jsonPath("$.error").doesNotExist());
    }

    @Test
    void authenticate_withBadCredentials_shouldReturnError() throws Exception {
        AuthenticationRequest request = new AuthenticationRequest("wrong@example.com", "wrongpass");

        Mockito.when(authenticationService.authenticate(any(AuthenticationRequest.class)))
                .thenThrow(new org.springframework.security.authentication.BadCredentialsException("Invalid"));

        mockMvc.perform(post("/api/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Error: Invalid username or password!"));
    }
}
