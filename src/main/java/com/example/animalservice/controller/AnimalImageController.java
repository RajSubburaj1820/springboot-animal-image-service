
package com.example.animalservice.controller;

import com.example.animalservice.model.AnimalImage;
import com.example.animalservice.service.AnimalImageService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> fetchImages(@RequestParam String type, @RequestParam int count) {
        try {
            service.fetchAndSaveAnimalImages(type, count);
            return ResponseEntity.ok(count + " images of " + type + " saved successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching images: " + e.getMessage());
        }
    }

    @GetMapping("/last")
    public ResponseEntity<List<AnimalImage>> getLastImages(@RequestParam String type, @RequestParam int count) {
        List<AnimalImage> images = service.getLastSavedAnimalImages(type, count);
        if (images.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(images);
    }

    @GetMapping("/last-stored")
    public ResponseEntity<AnimalImage> getLastStored(@RequestParam String type) {
        AnimalImage image = service.getLastSavedAnimalImage(type);
        if (image == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(image);
    }
}
