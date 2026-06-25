package com.example.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
public class FeatureFlagControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllFlags_shouldReturn200() throws Exception {
        mockMvc.perform(get("/flags"))
            .andExpect(status().isOk());
    }
    @Test
    void createFlag_shouldReturn201() throws Exception{
        mockMvc.perform(post("/flags")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\": \"test-flag\", \"enabled\": true}"))
            .andExpect(status().isCreated());
    }
    @Test
    void getNon_existentFlag() throws Exception{
        mockMvc.perform(get("/flags/{id}", 999L))
        .andExpect(status().isNotFound());
    }

    @Test
    void getEvaluate() throws Exception{
        mockMvc.perform(post("/flags")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\": \"test-flag2\", \"enabled\": true}"));
        mockMvc.perform(get("/flags/test-flag2/evaluate"))
            .andExpect(status().isOk());
    }
}
