package br.com.nexus.goat.services;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.nexus.goat.dto.OrderDTO;
import br.com.nexus.goat.dto.OrderDTO.Items;
import br.com.nexus.goat.entities.Order;
import br.com.nexus.goat.entities.OrderProduct;
import br.com.nexus.goat.entities.Product;
import br.com.nexus.goat.enums.OrderStatus;
import br.com.nexus.goat.exceptions.IncompleteDataException;
import br.com.nexus.goat.exceptions.NotFoundException;
import br.com.nexus.goat.repositories.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Transactional
    public Order findById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Pedido"));

        Hibernate.initialize(order.getProducts());

        return order;
    }

    @Transactional
    public List<Order> findAllByUserId(Long userId) {
        List<Order> results = orderRepository.findAllByAddressUserId(userId)
                .orElseThrow(() -> new NotFoundException("Pedido"));

        for (Order order : results) {
            Hibernate.initialize(order.getProducts());
        }

        return results;
    }

    @Transactional
    public Order save(Order order) {
        try {
            Random r = new Random();
            order.setOrderNumber(orderRepository.count());
            OrderStatus[] status = OrderStatus.values();

            order.setStatus(status[r.nextInt(status.length)]);

            return orderRepository.save(order);
        } catch (Exception e) {
            throw new IncompleteDataException();
        }
    }

    @Transactional
    public void deleteById(Long id) {
        orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Pedido"));
        orderRepository.deleteById(id);
    }

    public Set<OrderProduct> orderProducts(OrderDTO obj) {
        Set<OrderProduct> orderProducts = new HashSet<>();

        for (Items x : obj.getItems()) {
            Product product = productService.findById(x.getIdProduct());

            OrderProduct orderProduct = new OrderProduct(null, product, x.getQuantity());

            orderProducts.add(orderProduct);
        }
        return orderProducts;
    }
}
