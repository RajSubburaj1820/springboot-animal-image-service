package com.example.animalservice.service;

import com.example.animalservice.model.AnimalImage;
import com.example.animalservice.repository.AnimalImageRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;

@Service
public class AnimalImageService {
    private final AnimalImageRepository repository;
    private final RestTemplate restTemplate = new RestTemplate();

    public AnimalImageService(AnimalImageRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void initializeDatabase() {
        System.out.println("Fetching 10 images each for Cat, Dog, and Bear and storing them in DB...");
        fetchAndSaveAnimalImages("cat", 10);
        fetchAndSaveAnimalImages("dog", 10);
        fetchAndSaveAnimalImages("bear", 10);
    }

    public void fetchAndSaveAnimalImages(String type, int count) {
        String url = switch (type.toLowerCase()) {
            case "cat" -> "https://api.thecatapi.com/v1/images/search";
            case "dog" -> "https://random.dog/woof.json";
            case "bear" -> "https://placebear.com/400/400";
            default -> throw new IllegalArgumentException("Invalid animal type");
        };

        for (int i = 0; i < count; i++) {
            AnimalImage image = new AnimalImage();
            image.setType(type);

            if ("cat".equalsIgnoreCase(type)) {
                ImageResponse[] response = restTemplate.getForObject(url, ImageResponse[].class);
                if (response != null && response.length > 0) {
                    image.setImageUrl(response[0].getUrl());
                }
            } else if ("dog".equalsIgnoreCase(type)) {
                DogResponse response = restTemplate.getForObject(url, DogResponse.class);
                if (response != null && response.getUrl() != null && response.getUrl().endsWith(".jpg")) {
                    image.setImageUrl(response.getUrl());
                }
            } else if ("bear".equalsIgnoreCase(type)) {
                image.setImageUrl(url + "?random=" + new Random().nextInt(1000));
            }

            repository.save(image);
        }
    }

    public AnimalImage getLastSavedAnimalImage(String type) {
        return repository.findTopByTypeOrderByIdDesc(type).orElse(null);
    }

    public List<AnimalImage> getLastSavedAnimalImages(String type, int count) {
        return repository.findTopNByTypeOrderByIdDesc(type, count);
    }

    static class ImageResponse {
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    static class DogResponse {
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
