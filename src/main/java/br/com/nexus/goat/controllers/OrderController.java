package br.com.nexus.goat.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nexus.goat.models.Address;
import br.com.nexus.goat.models.Order;
import br.com.nexus.goat.models.OrderProduct;
import br.com.nexus.goat.models.dto.OrderDTO;
import br.com.nexus.goat.repositories.AddressRepository;
import br.com.nexus.goat.repositories.OrderProductRepository;
import br.com.nexus.goat.repositories.OrderRepository;
import br.com.nexus.goat.services.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private OrderService service;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @PostMapping("/{id_address}")
    public ResponseEntity<?> create(@PathVariable Long id_address, @RequestBody OrderDTO obj) {
        Address address = this.addressRepository.findById(id_address).orElse(null);

        Order order = this.service.order(obj);
        order.setAddress(address);
        order = this.repository.save(order);

        List<OrderProduct> orderProducts = this.service.orderProducts(obj);

        for (OrderProduct orderProduct : orderProducts) {
            orderProduct.setOrder(order);
            orderProduct = this.orderProductRepository.save(orderProduct);
            order.getProducts().add(orderProduct);
        }

        order = this.repository.save(order);

        return ResponseEntity.ok().body(order);
    }

    @GetMapping("/{id_address}")
    public ResponseEntity<?> create(@PathVariable Long id_address) {
        Address address = this.addressRepository.findById(id_address).orElse(null);

        List<Order> order = this.repository.findAllByAddress(address);

        return ResponseEntity.ok().body(order);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        this.repository.deleteById(id);
        return ResponseEntity.ok().body("O pedido foi deletado");
    }

}
