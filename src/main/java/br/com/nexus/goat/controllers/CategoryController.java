package br.com.nexus.goat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nexus.goat.entities.Category;
import br.com.nexus.goat.services.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    public ResponseEntity<Category> get(@PathVariable Long id) {
        Category category = this.service.findById(id);
        return ResponseEntity.ok().body(category);
    }
}
