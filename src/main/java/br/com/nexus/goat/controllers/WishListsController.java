package br.com.nexus.goat.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nexus.goat.models.Product;
import br.com.nexus.goat.models.User;
import br.com.nexus.goat.models.WishList;
import br.com.nexus.goat.repositories.ProductRepository;
import br.com.nexus.goat.repositories.UserRepository;
import br.com.nexus.goat.repositories.WishListRepository;

@RestController
@RequestMapping("/wishes")
public class WishListsController {

    @Autowired
    private WishListRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/{id_user}")
    public ResponseEntity<?> post(@PathVariable Long id_user, @RequestBody List<Long> id_products) {
        User user = this.userRepository.findById(id_user).orElse(null);
        System.out.println(id_products);

        for (Long id : id_products) {
            Product product = this.productRepository.findById(id).orElse(null);
            WishList wish = new WishList(user, product, null);
            wish = this.repository.save(wish);
            user.getWishes().add(wish);
        }
        user = this.userRepository.save(user);

        return ResponseEntity.ok().body(user);
    }
}
