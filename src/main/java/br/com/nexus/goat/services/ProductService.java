package br.com.nexus.goat.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nexus.goat.entities.Category;
import br.com.nexus.goat.entities.Feature;
import br.com.nexus.goat.entities.Product;
import br.com.nexus.goat.entities.dto.ProductDTO;
import br.com.nexus.goat.entities.dto.ProductDTO.Categories;
import br.com.nexus.goat.repositories.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Product findById(Long id) {
        return this.repository.findById(id).orElse(null);
    }

    public List<Product> findAll() {
        return this.repository.findAll();
    }

    public Product save(Product product) {
        product = this.repository.save(product);
        return product;
    }

    public void deleteById(Long id) {
        this.repository.deleteById(id);
    }

    public Product product(ProductDTO obj) {
        return new Product(null, obj.getName(), obj.getDescription(), obj.getPrice(), obj.getSize(),
                obj.getStock(), obj.getImgUrl());
    }

    public List<Category> categories(ProductDTO obj) {
        List<Category> categories = new ArrayList<>();
        for (Categories x : obj.getCategories()) {
            Category category = new Category(null, x.getName());
            categories.add(category);
        }
        return categories;
    }

    public Feature feature(ProductDTO obj) {
        return new Feature(null, obj.getFeatures().getMark(), obj.getFeatures().getModel(),
                obj.getFeatures().getComposition(), obj.getFeatures().getColor());
    }
}
