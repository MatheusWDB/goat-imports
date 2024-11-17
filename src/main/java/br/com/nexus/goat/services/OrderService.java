package br.com.nexus.goat.services;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.nexus.goat.dto.OrderDTO;
import br.com.nexus.goat.dto.OrderDTO.Items;
import br.com.nexus.goat.entities.Order;
import br.com.nexus.goat.entities.OrderProduct;
import br.com.nexus.goat.entities.Product;
import br.com.nexus.goat.enums.OrderStatus;
import br.com.nexus.goat.enums.PaymentMethod;
import br.com.nexus.goat.exceptions.IncompleteDataException;
import br.com.nexus.goat.exceptions.NotFoundException;
import br.com.nexus.goat.repositories.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private ProductService productService;

    @Transactional
    public Order findById(Long id) {
        return this.repository.findById(id).orElseThrow(() -> new NotFoundException("Pedido"));
    }

    @Transactional
    public List<Order> findAllByAddressId(Long idAddress) {
        return this.repository.findAllByAddressId(idAddress).orElseThrow(() -> new NotFoundException("Pedido"));
    }

    @Transactional
    public Order save(Order order) {
        try {
            Random r = new Random();
            order.setOrderNumber(this.repository.count());
            OrderStatus[] status = OrderStatus.values();
            PaymentMethod[] payment = PaymentMethod.values();

            order.setStatus(status[r.nextInt(status.length)]);
            if (order.getStatus() == OrderStatus.WAITING_PAYMENT) {
                order.setPaymentMethod(PaymentMethod.NULL);
            } else {
                order.setPaymentMethod(payment[r.nextInt(payment.length - 1)]);
            }

            return this.repository.save(order);
        } catch (Exception e) {
            throw new IncompleteDataException();
        }
    }

    @Transactional
    public void deleteById(Long id) {
        this.repository.findById(id).orElseThrow(() -> new NotFoundException("Pedido"));
        this.repository.deleteById(id);
    }

    public Set<OrderProduct> orderProducts(OrderDTO obj) {
        Set<OrderProduct> orderProducts = new HashSet<>();

        for (Items x : obj.getItems()) {
            Product product = this.productService.findById(x.getIdProduct());

            OrderProduct orderProduct = new OrderProduct(null, product, x.getQuantity());

            orderProducts.add(orderProduct);
        }
        return orderProducts;
    }
}
