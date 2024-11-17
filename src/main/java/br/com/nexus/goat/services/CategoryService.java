package br.com.nexus.goat.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.nexus.goat.entities.Category;
import br.com.nexus.goat.exceptions.IncompleteDataException;
import br.com.nexus.goat.exceptions.NotFoundException;
import br.com.nexus.goat.repositories.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Transactional
    public Category findById(Long id) {
        return this.repository.findById(id).orElseThrow(() -> new NotFoundException("Categoria"));
    }

    @Transactional
    public List<Category> findAll() {
        return this.repository.findAll();
    }

    @Transactional
    public Category findByName(String name) {
        return this.repository.findByName(name).orElse(null);
    }

    @Transactional
    public Category save(Category category) {
        try {
            return this.repository.save(category);
        } catch (Exception e) {
            throw new IncompleteDataException();
        }
    }
}
