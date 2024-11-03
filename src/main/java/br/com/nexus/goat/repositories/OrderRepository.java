package br.com.nexus.goat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nexus.goat.models.Address;
import br.com.nexus.goat.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByAddress(Address address);
}
