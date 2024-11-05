package br.com.nexus.goat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nexus.goat.models.OrderProduct;
import br.com.nexus.goat.repositories.OrderProductRepository;

@Service
public class OrderProductService {
    
    @Autowired
    private OrderProductRepository repository;

    public OrderProduct save(OrderProduct orderProduct){
        orderProduct = this.repository.save(orderProduct);
        return orderProduct;
    }
}
