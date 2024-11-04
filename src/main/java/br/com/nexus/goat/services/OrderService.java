package br.com.nexus.goat.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nexus.goat.models.Order;
import br.com.nexus.goat.models.OrderProduct;
import br.com.nexus.goat.models.Product;
import br.com.nexus.goat.models.dto.OrderDTO;
import br.com.nexus.goat.models.dto.OrderDTO.Products;
import br.com.nexus.goat.repositories.ProductRepository;

@Service
public class OrderService {

    @Autowired 
    private ProductRepository productRepository;

    public Order order(OrderDTO obj){
        Order order = new Order(null, obj.getStatus(), obj.getPaymentMethod(), obj.getOrderNumber());
        return order;
    }

    public List<OrderProduct> orderProducts(OrderDTO obj){        
        List<OrderProduct> orderProducts = new ArrayList<>();

        for (Products x : obj.getProducts()) {
            OrderProduct orderProduct = new OrderProduct();

            Product product = this.productRepository.findById(x.getId()).orElse(null);            

            System.out.println(product);

            orderProduct.setProduct(product);;
            orderProduct.setQuantity(x.getQuantity());

            orderProducts.add(orderProduct);
        }
        return orderProducts;
    }
}
