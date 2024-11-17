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
import br.com.nexus.goat.services.FeatureService;
import br.com.nexus.goat.services.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FeatureService featureService;

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody Product obj) {
        Product product = this.service.product(obj);
        Set<Category> categories = this.service.categories(obj);
        Feature feature = this.service.feature(obj);

        product = this.service.save(product);

        Feature verifyFeature = this.featureService.findByMarkAndModelAndColorAndComposition(feature.getMark(),
                feature.getModel(), feature.getColor(), feature.getComposition());

        if (verifyFeature == null)
            feature = this.featureService.save(feature);
        else
            feature = verifyFeature;

        product.setFeatures(feature);

        for (Category category : categories) {
            Category verifyCategory = this.categoryService.findByName(category.getName());
            if (verifyCategory == null)
                category = this.categoryService.save(category);
            else
                category = verifyCategory;

            product.getCategories().add(category);
        }

        product = this.service.save(product);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<ProductDTO>> findAll() {
        List<ProductDTO> products = this.service.findAll();
        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        Product result = this.service.findById(id);
        ProductDTO product = new ProductDTO(result);
        return ResponseEntity.ok().body(product);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
