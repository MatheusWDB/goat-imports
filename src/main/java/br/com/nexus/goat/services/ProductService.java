package br.com.nexus.goat.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.nexus.goat.models.Category;
import br.com.nexus.goat.models.Product;
import br.com.nexus.goat.models.dto.ProductDTO;
import br.com.nexus.goat.models.dto.ProductDTO.Categories;

@Service
public class ProductService {

    public Product product(ProductDTO obj) {
        Product product = new Product(null, obj.getName(), obj.getDescription(), obj.getPrice(), obj.getSize(),
                obj.getStock(), obj.getImgUrl());

        return product;
    }

    public List<Category> categories(ProductDTO obj) {
        List<Category> categories = new ArrayList<>();
        for (Categories x : obj.getCategories()) {
            Category category = new Category(null, x.getName());
            categories.add(category);
        }
        return categories; 
    }
}
