package com.example.animalservice.service;

import com.example.animalservice.model.AnimalImage;
import com.example.animalservice.repository.AnimalImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AnimalImageService {

    private final AnimalImageRepository repository;
    private final RestTemplate restTemplate;

    public AnimalImageService(AnimalImageRepository repository) {
        this.repository = repository;
        this.restTemplate = new RestTemplate();
    }

    public void fetchAndSaveAnimalImages(String type, int count) {
        List<AnimalImage> images = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            String imageUrl = fetchImageUrl(type);
            if (imageUrl != null) {
                AnimalImage image = new AnimalImage();
                image.setType(type);
                image.setImageUrl(imageUrl);
                images.add(image);
            }
        }

        repository.saveAll(images);
    }

    private String fetchImageUrl(String type) {
        try {
            switch (type.toLowerCase()) {
                case "cat":
                    Map[] catResponse = restTemplate.getForObject("https://api.thecatapi.com/v1/images/search", Map[].class);
                    return (String) (catResponse != null && catResponse.length > 0 ? catResponse[0].get("url") : null);
                case "dog":
                    Map<String, Object> dogResponse = restTemplate.getForObject("https://random.dog/woof.json", Map.class);
                    return (String) dogResponse.get("url");
                case "bear":
                    int width = 300 + (int)(Math.random() * 100);
                    int height = 300 + (int)(Math.random() * 100);
                    return "https://placebear.com/" + width + "/" + height;
                default:
                    return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List<AnimalImage> getLastSavedAnimalImages(String type, int count) {
        return repository.findTopNByTypeOrderByIdDesc(type, count);
    }

    public AnimalImage getLastSavedAnimalImage(String type) {
        List<AnimalImage> images = repository.findTopNByTypeOrderByIdDesc(type, 1);
        return images.isEmpty() ? null : images.get(0);
    }
}
