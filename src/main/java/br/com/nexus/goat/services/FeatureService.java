package br.com.nexus.goat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.nexus.goat.entities.Feature;
import br.com.nexus.goat.exceptions.IncompleteDataException;
import br.com.nexus.goat.exceptions.NotFoundException;
import br.com.nexus.goat.repositories.FeatureRepository;

@Service
public class FeatureService {

    @Autowired
    private FeatureRepository repository;

    @Transactional
    public Feature findById(Long id) {
        return this.repository.findById(id).orElseThrow(() -> new NotFoundException("Caracaterística"));
    }

    @Transactional
    public Feature findByMarkAndModelAndColorAndComposition(String mark, String model, String color,
            String composition) {
        return this.repository.findByMarkAndModelAndColorAndComposition(mark, model, color, composition).orElse(null);
    }

    @Transactional
    public Feature save(Feature feature) {
        try {
            return this.repository.save(feature);
        } catch (Exception e) {
            throw new IncompleteDataException();
        }
    }
}
