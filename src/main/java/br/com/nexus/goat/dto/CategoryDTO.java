package br.com.nexus.goat.dto;

import org.springframework.beans.BeanUtils;

import br.com.nexus.goat.entities.Category;

public class CategoryDTO {

    private Long id;
    private String name;

    public CategoryDTO(Category entity) {
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
}
