package br.com.nexus.goat.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nexus.goat.dto.CategoryDTO;
import br.com.nexus.goat.entity.Category;
import br.com.nexus.goat.services.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping("/findAll")
    public ResponseEntity<List<CategoryDTO>> findAll(){
        List<Category> results = this.service.findAll();
        List<CategoryDTO> categories = results.stream().map(x-> new CategoryDTO(x)).toList();
        return ResponseEntity.ok().body(categories);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id) {
        Category category = this.service.findById(id);
        return ResponseEntity.ok().body(category);
    }
}
