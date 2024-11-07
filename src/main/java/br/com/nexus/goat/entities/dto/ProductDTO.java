package br.com.nexus.goat.entities.dto;

import java.util.Set;

import lombok.Data;

@Data
public class ProductDTO {
    private String name;
    private String description;
    private String size;
    private Double price;
    private Integer stock;
    private String imgUrl;
    private Features features;
    private Set<Categories> categories;

    @Data
    public static class Features {
        private String model;
        private String mark;
        private String color;
        private String composition;
    }

    @Data
    public static class Categories {
        private String name;
    }
}
