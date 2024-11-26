package br.com.nexus.goat.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nexus.goat.dto.ProductDTO;
import br.com.nexus.goat.entities.Category;
import br.com.nexus.goat.entities.Feature;
import br.com.nexus.goat.entities.Product;
import br.com.nexus.goat.services.CategoryService;
import br.com.nexus.goat.services.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody Product obj) {
        Product product = productService.product(obj);
        Set<Category> categories = productService.categories(obj);
        Feature feature = productService.feature(obj);

        product = productService.save(product);

        product.setFeatures(feature);

        for (Category category : categories) {
            Category verifyCategory = categoryService.findByName(category.getName());
            if (verifyCategory == null)
                category = categoryService.save(category);
            else
                category = verifyCategory;

            product.getCategories().add(category);
        }

        productService.save(product);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<ProductDTO>> findAll() {
        List<ProductDTO> products = productService.findAll();
        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        Product result = productService.findById(id);
        ProductDTO product = new ProductDTO(result);
        return ResponseEntity.ok().body(product);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
