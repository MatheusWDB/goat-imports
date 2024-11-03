package br.com.nexus.goat.controllers;

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
import br.com.nexus.goat.repositories.AddressRepository;
import br.com.nexus.goat.repositories.OrderRepository;

@RestController
@RequestMapping("/orders")
public class OrderController {
    
    @Autowired
    private OrderRepository repository;

    @Autowired
    private AddressRepository addressRepository;

    @PostMapping("/{id_address}")
    public ResponseEntity<?> create(@PathVariable Long id_address, @RequestBody Order obj){
        Address address = this.addressRepository.findById(id_address).orElse(null);
        obj.setAddress(address);

        Order order = this.repository.save(obj);

        return ResponseEntity.ok().body(order);
    }

    @GetMapping("/{id_address}")
    public ResponseEntity<?> create(@PathVariable Long id_address){
        Address address = this.addressRepository.findById(id_address).orElse(null);
        Order order = this.repository.findByAddress(address);

        return ResponseEntity.ok().body(order);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        this.repository.deleteById(id);
        return ResponseEntity.ok().body("O pedido foi deletado");
    }

}
