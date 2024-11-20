package br.com.nexus.goat.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nexus.goat.dto.CategoryDTO;
import br.com.nexus.goat.entities.Category;
import br.com.nexus.goat.services.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/findAll")
    public ResponseEntity<List<CategoryDTO>> findAll(){
        List<Category> results = categoryService.findAll();
        List<CategoryDTO> categories = results.stream().map(x-> new CategoryDTO(x)).toList();
        return ResponseEntity.ok().body(categories);
    }

    @GetMapping("/findAllByIdProduct/{idProduct}")
    public ResponseEntity<List<CategoryDTO>> findAllByIdProduct(@PathVariable Long idProduct) {
        List<CategoryDTO> category = categoryService.findAllByIdProduct(idProduct);
        return ResponseEntity.ok().body(category);
    }
}
