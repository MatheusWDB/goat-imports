package br.com.nexus.goat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nexus.goat.entities.Feature;
import br.com.nexus.goat.repositories.FeatureRepository;

@Service
public class FeatureService {

    @Autowired
    private FeatureRepository repository;

    public Feature findByMarkAndModelAndColorAndComposition(String mark, String model, String color,
            String composition) {
        return this.repository.findByMarkAndModelAndColorAndComposition(mark, model, color, composition);
    }

    public Feature save(Feature feature) {
        feature = this.repository.save(feature);
        return feature;
    }
}
