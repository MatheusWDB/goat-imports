package br.com.nexus.goat.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nexus.goat.entities.Feature;

public interface FeatureRepository extends JpaRepository<Feature, Long> {
    Optional<Feature> findByProductId(Long productId);
}