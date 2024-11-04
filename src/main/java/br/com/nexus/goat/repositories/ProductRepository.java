package br.com.nexus.goat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nexus.goat.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {    
}
