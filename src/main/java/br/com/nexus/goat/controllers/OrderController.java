package br.com.nexus.goat.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nexus.goat.dto.OrderDTO;
import br.com.nexus.goat.entities.Address;
import br.com.nexus.goat.entities.Order;
import br.com.nexus.goat.entities.OrderProduct;
import br.com.nexus.goat.services.AddressService;
import br.com.nexus.goat.services.OrderProductService;
import br.com.nexus.goat.services.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private OrderProductService orderProductService;

    @PostMapping("/create/{idAddress}")
    public ResponseEntity<Void> create(@PathVariable Long idAddress, @RequestBody OrderDTO body) {
        Address address = addressService.findById(idAddress);
        Order order = new Order();        

        order.setPaymentMethod(body.getPaymentMethod());
        order.setAddress(address);

        order = orderService.save(order);
        Set<OrderProduct> orderProducts = orderService.orderProducts(body);

        for (OrderProduct orderProduct : orderProducts) {
            orderProduct.setOrder(order);
            orderProduct = orderProductService.save(orderProduct);

            order.getProducts().add(orderProduct);
        }

        orderService.save(order);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Order> findById(@PathVariable Long id) {
        Order order = orderService.findById(id);

        return ResponseEntity.ok().body(order);
    }

    @GetMapping("/findAllByUserId/{userId}")
    public ResponseEntity<List<Order>> findAll(@PathVariable Long userId) {
        List<Order> orders = orderService.findAllByUserId(userId);

        return ResponseEntity.ok().body(orders);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
