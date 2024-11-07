package br.com.nexus.goat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nexus.goat.entities.Address;
import br.com.nexus.goat.entities.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByAddress(Address address);

    @Override
    default long count() {
        throw new UnsupportedOperationException("Unimplemented method 'count'");
    }
}
