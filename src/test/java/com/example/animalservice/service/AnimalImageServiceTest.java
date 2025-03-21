package com.example.animalservice.service;

import com.example.animalservice.model.AnimalImage;
import com.example.animalservice.repository.AnimalImageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AnimalImageServiceTest {

    @Autowired
    private AnimalImageService service;

    @Autowired
    private AnimalImageRepository repository;

    @Test
    void shouldFetchAndSaveAnimalImages() {
        service.fetchAndSaveAnimalImages("cat", 2);
        List<AnimalImage> images = repository.findTopNByTypeOrderByIdDesc("cat", 2);
        assertEquals(2, images.size());
    }

    @Test
    void shouldReturnLastSavedAnimalImage() {
        service.fetchAndSaveAnimalImages("cat", 1);
        AnimalImage image = service.getLastSavedAnimalImage("cat");
        assertNotNull(image);
        assertEquals("cat", image.getType());
    }

    @Test
    void shouldNotSaveImagesForInvalidAnimalType() {
        service.fetchAndSaveAnimalImages("unicorn", 1);
        List<AnimalImage> images = repository.findTopNByTypeOrderByIdDesc("unicorn", 1);
        assertTrue(images.isEmpty());
    }

    @Test
    void shouldReturnNullWhenNoImageExists() {
        List<AnimalImage> images = repository.findTopNByTypeOrderByIdDesc("sloth", 1);
        if (!images.isEmpty()) {
            repository.deleteAll(images); // Clear out existing test data if present
        }

        AnimalImage image = service.getLastSavedAnimalImage("sloth");
        assertNull(image);
    }
}
