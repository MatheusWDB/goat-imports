package br.com.nexus.goat.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nexus.goat.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByAddressId(Long idAddress);
}
