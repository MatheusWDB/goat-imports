package br.com.nexus.goat.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nexus.goat.entities.Category;
import br.com.nexus.goat.entities.Feature;
import br.com.nexus.goat.entities.Product;
import br.com.nexus.goat.exceptions.product.ProductNotFoundException;
import br.com.nexus.goat.repositories.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Product findById(Long id) {
        return this.repository.findById(id).orElseThrow(new ProductNotFoundException());
    }

    public List<Product> findAll() {
        List<Product> products = this.repository.findAll()
        if (products == null){
            throw new ProductNotFoundException();
        }
        return products;
    }

    public Product save(Product product) {
        product = this.repository.save(product);
        return product;
    }

    public void deleteById(Long id) {
        Product product = this.repository.findById(id);
        if (product == null){
            throw new ProductNotFoundException()
        }
        this.repository.deleteById(id)
    }

    public Product product(Product obj) {
        return new Product(null, obj.getName(), obj.getDescription(), obj.getPrice(), obj.getSize(),
                obj.getStock(), obj.getImgUrl());
    }

    public Set<Category> categories(Product obj) {
        Set<Category> categories = new HashSet<>();
        for (Category x : obj.getCategories()) {
            Category category = new Category(null, x.getName());
            categories.add(category);
        }
        return categories;
    }

    public Feature feature(Product obj) {
        return new Feature(null, obj.getFeatures().getMark(), obj.getFeatures().getModel(),
                obj.getFeatures().getComposition(), obj.getFeatures().getColor());
    }
}
