package br.com.nexus.goat.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.nexus.goat.dto.ProductDTO;
import br.com.nexus.goat.entities.Category;
import br.com.nexus.goat.entities.Feature;
import br.com.nexus.goat.entities.Product;
import br.com.nexus.goat.exceptions.IncompleteDataException;
import br.com.nexus.goat.exceptions.NotFoundException;
import br.com.nexus.goat.repositories.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Transactional
    public Product findById(Long id) {
        Product product = repository.findById(id).orElseThrow(() -> new NotFoundException("Produto"));
        Hibernate.initialize(product.getCategories());
        return product;
    }

    @Transactional
    public List<ProductDTO> findAll() {
        List<Product> results = this.repository.findAll();
        for (Product product : results) {
            Hibernate.initialize(product.getCategories());
        }
        if (results.isEmpty()) {
            throw new NotFoundException("Produto");
        }
        return results.stream().map(x -> new ProductDTO(x)).toList();
    }

    @Transactional
    public Product save(Product product) {
        try {
            return this.repository.save(product);
        } catch (Exception e) {
            throw new IncompleteDataException();
        }
    }

    @Transactional
    public void deleteById(Long id) {
        this.repository.findById(id).orElseThrow(() -> new NotFoundException("Produto"));
        this.repository.deleteById(id);
    }

    public Product product(Product obj) {
        return new Product(obj.getName(), obj.getDescription(), obj.getPrice(),
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
