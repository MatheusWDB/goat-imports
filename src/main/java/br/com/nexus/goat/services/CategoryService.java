package br.com.nexus.goat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nexus.goat.entities.Category;
import br.com.nexus.goat.exceptions.IncompleteDataException;
import br.com.nexus.goat.exceptions.NotFoundException;
import br.com.nexus.goat.repositories.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public Category findById(Long id) {
        return this.repository.findById(id).orElseThrow(() -> new NotFoundException("Categoria"));
    }

    public Category findByName(String name) {
        return this.repository.findByName(name);
    }

    public Category save(Category category) {
        try {
            return this.repository.save(category);
        } catch (Exception e) {
            throw new IncompleteDataException();
        }
    }
}
