package br.com.nexus.goat.services;

import org.springframework.stereotype.Service;

import br.com.nexus.goat.models.Product;
import br.com.nexus.goat.models.dto.ProductDTO;

@Service
public class ProductService {

    public Product product(ProductDTO obj){
        Product product = new Product(null, obj.getName(), obj.getDescription(), obj.getPrice(), obj.getSize(), obj.getStock(), obj.getImgUrl());

        return product;
    }
}
