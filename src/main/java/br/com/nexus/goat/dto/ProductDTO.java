package br.com.nexus.goat.dto;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;

import br.com.nexus.goat.entity.Product;

public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private String size;
    private Integer stock;
    private String imgUrl;
    private Set<Long> categories = new HashSet<>();
    private Long features;

    public ProductDTO(Product entity) {
        BeanUtils.copyProperties(entity, this);
        categories = entity.getIdCategories();
        features = entity.getIdFeature();
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
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
        return categories;
    }

    public void setCategories(Set<Long> categories) {
        this.categories = categories;
    }

    public Long getFeatures() {
        return features;
    }

    public void setFeatures(Long features) {
        this.features = features;
    }
}
