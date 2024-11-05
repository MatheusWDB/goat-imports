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
import br.com.nexus.goat.services.AddressService;
import br.com.nexus.goat.services.OrderProductService;
import br.com.nexus.goat.services.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @Autowired
    private AddressService addressService;

    @Autowired
    private OrderProductService orderProductService;

    @PostMapping("/{idAddress}")
    public ResponseEntity<Order> create(@PathVariable Long idAddress, @RequestBody OrderDTO obj) {
        Address address = this.addressService.findById(idAddress);

        Order order = this.service.order(obj);
        order.setAddress(address);
        order = this.service.save(order);

        List<OrderProduct> orderProducts = this.service.orderProducts(obj);

        for (OrderProduct orderProduct : orderProducts) {
            orderProduct.setOrder(order);
            orderProduct = this.orderProductService.save(orderProduct);
            order.getProducts().add(orderProduct);
        }

        order = this.service.save(order);

        return ResponseEntity.ok().body(order);
    }

    @GetMapping("/{idAddress}")
    public ResponseEntity<List<Order>> create(@PathVariable Long idAddress) {
        Address address = this.addressService.findById(idAddress);

        List<Order> orders = this.service.findAllByAddress(address);

        return ResponseEntity.ok().body(orders);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        this.service.deleteById(id);
        return ResponseEntity.ok().body("O pedido foi deletado");
    }

}
