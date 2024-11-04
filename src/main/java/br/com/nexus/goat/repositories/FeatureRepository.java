package br.com.nexus.goat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nexus.goat.models.Feature;

public interface FeatureRepository extends JpaRepository<Feature, Long> {
    Feature findByMarkAndModelAndColorAndComposition(String mark, String model, String color, String composition);
}