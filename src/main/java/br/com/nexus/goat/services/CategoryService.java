package br.com.nexus.goat.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.nexus.goat.dto.CategoryDTO;
import br.com.nexus.goat.entities.Category;
import br.com.nexus.goat.entities.Product;
import br.com.nexus.goat.exceptions.IncompleteDataException;
import br.com.nexus.goat.exceptions.NotFoundException;
import br.com.nexus.goat.repositories.CategoryRepository;
import br.com.nexus.goat.repositories.ProductRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Categoria"));
    }

    @Transactional
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Transactional
    public List<CategoryDTO> findAllByIdProduct(Long idProduct) {
        Product product = productRepository.findById(idProduct).orElseThrow(() -> new NotFoundException("Produto"));
        List<Category> categories = new ArrayList<>();
        for (Category idCategory : product.getCategories()) {
            Category category = categoryRepository.findById(idCategory.getId())
                    .orElseThrow(() -> new NotFoundException("Categoria"));
            categories.add(category);
        }
        return categories.stream().map(x -> new CategoryDTO(x)).toList();
    }

    @Transactional
    public Category findByName(String name) {
        return categoryRepository.findByName(name).orElse(null);
    }

    @Transactional
    public Category save(Category category) {
        try {
            return categoryRepository.save(category);
        } catch (Exception e) {
            throw new IncompleteDataException();
        }
    }
}
