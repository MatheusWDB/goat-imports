package br.com.nexus.goat.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nexus.goat.exceptions.IncompleteDataException;
import br.com.nexus.goat.exceptions.NotFoundException;
import br.com.nexus.goat.models.Category;
import br.com.nexus.goat.models.Feature;
import br.com.nexus.goat.models.Product;
import br.com.nexus.goat.repositories.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Product findById(Long id) {
        return this.repository.findById(id).orElseThrow(() -> new NotFoundException("Produto"));
    }

    public List<Product> findAll() {
        List<Product> products = this.repository.findAll();
        if (products.isEmpty()) {
            throw new NotFoundException("Produto");
        }
        return products;
    }

    public Product save(Product product) {
        try {
            return this.repository.save(product);
        } catch (Exception e) {
            throw new IncompleteDataException();
        }
    }

    public void deleteById(Long id) {
        this.repository.findById(id).orElseThrow(() -> new NotFoundException("Produto"));
        this.repository.deleteById(id);
    }

    public Product product(Product obj) {
        return new Product(obj.getName(), obj.getDescription(), obj.getPrice(), obj.getSize(),
                obj.getStock(), obj.getImgUrl());
    }

    public Set<Category> categories(Product obj) {
        Set<Category> categories = new HashSet<>();
        for (Category x : obj.getCategories()) {
            Category category = new Category(x.getName());
            categories.add(category);
        }
        return categories;
    }

    public Feature feature(Product obj) {
        return new Feature(obj.getFeatures().getMark(), obj.getFeatures().getModel(),
                obj.getFeatures().getComposition(), obj.getFeatures().getColor());
    }
}
