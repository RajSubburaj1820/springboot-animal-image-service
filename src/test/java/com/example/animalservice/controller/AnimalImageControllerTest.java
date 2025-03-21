package com.example.animalservice.controller;

import com.example.animalservice.model.AnimalImage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AnimalImageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnLastCatImage() throws Exception {
        // First, fetch and store a Cat image
        mockMvc.perform(post("/api/animals/fetch")
                        .param("type", "cat")
                        .param("count", "1"))
                .andExpect(status().isOk());

        // Now retrieve the last Cat image
        mockMvc.perform(get("/api/animals/last")
                        .param("type", "cat")
                        .param("count", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("cat"));
    }

    @Test
    void shouldReturnLastBearImage() throws Exception {
        // First, fetch and store a bear image
        mockMvc.perform(post("/api/animals/fetch")
                        .param("type", "bear")
                        .param("count", "1"))
                .andExpect(status().isOk());

        // Now retrieve the last bear image
        mockMvc.perform(get("/api/animals/last")
                        .param("type", "bear")
                        .param("count", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("bear"));
    }
}
