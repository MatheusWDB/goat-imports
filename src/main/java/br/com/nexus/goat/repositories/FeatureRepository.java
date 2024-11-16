package br.com.nexus.goat.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nexus.goat.entity.Feature;

public interface FeatureRepository extends JpaRepository<Feature, Long> {
    Optional<Feature> findByMarkAndModelAndColorAndComposition(String mark, String model, String color, String composition);
}