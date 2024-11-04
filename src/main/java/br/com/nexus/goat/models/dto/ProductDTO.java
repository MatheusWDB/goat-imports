package br.com.nexus.goat.models.dto;

import java.util.List;

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
    private List<Categories> categories;

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
