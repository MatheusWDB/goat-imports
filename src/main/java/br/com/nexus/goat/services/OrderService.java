package br.com.nexus.goat.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nexus.goat.entities.Address;
import br.com.nexus.goat.entities.Order;
import br.com.nexus.goat.entities.OrderProduct;
import br.com.nexus.goat.entities.Product;
import br.com.nexus.goat.entities.dto.OrderDTO;
import br.com.nexus.goat.entities.dto.OrderDTO.Products;
import br.com.nexus.goat.repositories.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private ProductService productService;

    public Order findAllById(Long id) {
        return this.repository.findById(id).orElse(null);
    }

    public List<Order> findAllByAddress(Address address) {
        return this.repository.findAllByAddress(address);
    }

    public Order save(Order order) {
        order = this.repository.save(order);
        return order;
    }

    public void deleteById(Long id) {
        this.repository.deleteById(id);
    }

    public Order order(OrderDTO obj) {
        return new Order(null, obj.getStatus(), obj.getPaymentMethod(), obj.getOrderNumber());
    }

    public List<OrderProduct> orderProducts(OrderDTO obj) {
        List<OrderProduct> orderProducts = new ArrayList<>();

        for (Products x : obj.getProducts()) {
            OrderProduct orderProduct = new OrderProduct();

            Product product = this.productService.findById(x.getId());
            orderProduct.setProduct(product);
            orderProduct.setQuantity(x.getQuantity());

            orderProducts.add(orderProduct);
        }
        return orderProducts;
    }
}
