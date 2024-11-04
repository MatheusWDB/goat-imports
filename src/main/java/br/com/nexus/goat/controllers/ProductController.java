package br.com.nexus.goat.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nexus.goat.models.Category;
import br.com.nexus.goat.models.Product;
import br.com.nexus.goat.models.dto.ProductDTO;
import br.com.nexus.goat.repositories.CategoryRepository;
import br.com.nexus.goat.repositories.ProductRepository;
import br.com.nexus.goat.services.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductService service;

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ProductDTO obj) {
        Product product = this.service.product(obj);
        List<Category> categories = this.service.categories(obj);
        product = this.repository.save(product);

        for (Category category : categories) {
            Category verify = this.categoryRepository.findByName(category.getName());
            if (verify == null)
                category = this.categoryRepository.save(category);
            else
                category = verify;

            product.getCategories().add(category);
        }

        product = this.repository.save(product);

        return ResponseEntity.ok().body(product);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        List<Product> products = this.repository.findAll();
        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        Product product = this.repository.findById(id).orElse(null);
        return ResponseEntity.ok().body(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        this.repository.deleteById(id);
        return ResponseEntity.ok().body("Produto deletado!");
    }
}
