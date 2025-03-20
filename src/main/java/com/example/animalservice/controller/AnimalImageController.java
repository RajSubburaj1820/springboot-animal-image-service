package com.example.animalservice.controller;

import com.example.animalservice.model.AnimalImage;
import com.example.animalservice.service.AnimalImageService;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/animals")
public class AnimalImageController {
    private final AnimalImageService service;

    public AnimalImageController(AnimalImageService service) {
        this.service = service;
    }

    @PostMapping("/fetch")
    public String fetchImages(@RequestParam String type, @RequestParam int count) {
        service.fetchAndSaveAnimalImages(type, count);
        return count + " images of " + type + " saved successfully.";
    }

    @GetMapping("/last")
    public List<AnimalImage> getLastImages(@RequestParam String type, @RequestParam int count) {
        return service.getLastSavedAnimalImages(type, count);
    }

    @GetMapping("/last-stored")
    public AnimalImage getLastStoredImage(@RequestParam String type) {
        return service.getLastSavedAnimalImage(type);
    }

    @PostConstruct
    public void preFetchLastStoredImages() {
        System.out.println("Preloading last stored images...");
        service.fetchAndSaveAnimalImages("cat", 1);
        service.fetchAndSaveAnimalImages("dog", 1);
        service.fetchAndSaveAnimalImages("bear", 1);
        System.out.println("Preloaded images are now available.");
    }
}
