package br.com.nexus.goat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nexus.goat.exceptions.IncompleteDataException;
import br.com.nexus.goat.models.OrderProduct;
import br.com.nexus.goat.repositories.OrderProductRepository;

@Service
public class OrderProductService {

    @Autowired
    private OrderProductRepository repository;

    public OrderProduct save(OrderProduct orderProduct) {
        try {
            return this.repository.save(orderProduct);
        } catch (Exception e) {
            throw new IncompleteDataException();
        }
    }
}
