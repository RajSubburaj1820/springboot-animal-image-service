package com.example.animalservice.repository;

import com.example.animalservice.model.AnimalImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnimalImageRepository extends JpaRepository<AnimalImage, Long> {

    @Query("SELECT a FROM AnimalImage a WHERE a.type = :type ORDER BY a.id DESC LIMIT :count")
    List<AnimalImage> findTopNByTypeOrderByIdDesc(@Param("type") String type, @Param("count") int count);

    Optional<AnimalImage> findTopByTypeOrderByIdDesc(String type);
}