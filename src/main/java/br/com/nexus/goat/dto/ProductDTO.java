package br.com.nexus.goat.dto;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;

import br.com.nexus.goat.entities.Category;
import br.com.nexus.goat.entities.Feature;
import br.com.nexus.goat.entities.Product;

public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private String imgUrl;
    private Set<Category> categories = new HashSet<>();
    private Feature features;

    public ProductDTO(Product entity) {
        BeanUtils.copyProperties(entity, this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Set<Long> getCategories() {
        Set<Long> idCategories = new HashSet<>();
        for (Category category : categories) {
            Long idCategory = category.getId();
            idCategories.add(idCategory);
        }
        return idCategories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Long getFeatures() {
        return features.getId();
    }

    public void setFeatures(Feature features) {
        this.features = features;
    }
}
