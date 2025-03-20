package com.example.animalservice.controller;

import com.example.animalservice.model.AnimalImage;
import com.example.animalservice.service.AnimalImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AnimalImageControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AnimalImageService service;

    @InjectMocks
    private AnimalImageController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void shouldFetchAnimalImages() throws Exception {
        mockMvc.perform(post("/api/animals/fetch")
                        .param("type", "cat")
                        .param("count", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service, times(1)).fetchAndSaveAnimalImages("cat", 1);
    }

    @Test
    void shouldReturnLastAnimalImage() throws Exception {
        AnimalImage image = new AnimalImage();
        image.setType("cat");
        image.setImageUrl("https://placekitten.com/400/400");

        when(service.getLastSavedAnimalImages("cat", 1)).thenReturn(List.of(image));

        mockMvc.perform(get("/api/animals/last")
                        .param("type", "cat")  // ✅ Ensure type is passed
                        .param("count", "1")   // ✅ Ensure count is passed
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // Expecting 200 OK
                .andExpect(jsonPath("$[0].type").value("cat"))
                .andExpect(jsonPath("$[0].imageUrl").value("https://placekitten.com/400/400"));
    }
}