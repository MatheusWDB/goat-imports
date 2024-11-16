package br.com.nexus.goat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nexus.goat.entity.OrderProduct;
import br.com.nexus.goat.entity.pk.OrderProductPK;

public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductPK> {
}
