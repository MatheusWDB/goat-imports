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
    private FeatureRepository featureRepository;

    @Transactional
    public Feature findById(Long id) {
        return featureRepository.findById(id).orElseThrow(() -> new NotFoundException("Caracaterística"));
    }

    @Transactional
    public Feature findByProductId(Long productId) {
        return featureRepository.findByProductId(productId).orElseThrow(() -> new NotFoundException("Caracaterística"));
    }

    @Transactional
    public Feature save(Feature feature) {
        try {
            return featureRepository.save(feature);
        } catch (Exception e) {
            throw new IncompleteDataException();
        }
    }
}
