package br.com.nexus.goat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nexus.goat.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}