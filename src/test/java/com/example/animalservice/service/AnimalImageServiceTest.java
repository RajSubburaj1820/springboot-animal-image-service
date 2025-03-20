package com.example.animalservice.service;

import com.example.animalservice.model.AnimalImage;
import com.example.animalservice.repository.AnimalImageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnimalImageServiceTest {

    @Mock
    private AnimalImageRepository repository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private AnimalImageService service;

    @BeforeEach
    void setUp() {
        service = new AnimalImageService(repository);
    }

    @Test
    void shouldFetchAndSaveCatImages() {
        when(repository.save(any(AnimalImage.class))).thenReturn(new AnimalImage());

        service.fetchAndSaveAnimalImages("cat", 2);

        verify(repository, times(2)).save(any(AnimalImage.class));
    }

    @Test
    void shouldFetchAndSaveDogImages() {
        when(repository.save(any(AnimalImage.class))).thenReturn(new AnimalImage());

        service.fetchAndSaveAnimalImages("dog", 2);

        verify(repository, times(2)).save(any(AnimalImage.class));
    }

    @Test
    void shouldFetchAndSaveBearImages() {
        when(repository.save(any(AnimalImage.class))).thenReturn(new AnimalImage());

        service.fetchAndSaveAnimalImages("bear", 2);

        verify(repository, times(2)).save(any(AnimalImage.class));
    }

    @Test
    void shouldReturnLastSavedAnimalImage() {
        AnimalImage expectedImage = new AnimalImage();
        expectedImage.setType("cat");
        expectedImage.setImageUrl("https://placekitten.com/400/400");

        when(repository.findTopByTypeOrderByIdDesc("cat")).thenReturn(Optional.of(expectedImage));

        AnimalImage actualImage = service.getLastSavedAnimalImage("cat");

        assertNotNull(actualImage);
        assertEquals("cat", actualImage.getType());
        assertEquals("https://placekitten.com/400/400", actualImage.getImageUrl());
    }
}