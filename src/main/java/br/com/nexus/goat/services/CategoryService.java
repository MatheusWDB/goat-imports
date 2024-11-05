package br.com.nexus.goat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nexus.goat.models.Category;
import br.com.nexus.goat.repositories.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public Category findByName(String name) {
        return this.repository.findByName(name);
    }

    public Category save(Category category) {
        category = this.repository.save(category);
        return category;
    }
}
