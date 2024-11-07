package br.com.nexus.goat.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.nexus.goat.entities.Product;
import br.com.nexus.goat.entities.User;
import br.com.nexus.goat.entities.dto.UserDTO;
import br.com.nexus.goat.services.ProductService;
import br.com.nexus.goat.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private ProductService productService;

    @PostMapping("/register")
    public ResponseEntity<User> create(@RequestBody User newUser) {
        this.service.verifyEmail(newUser.getEmail());
        this.service.verifyPhone(newUser.getPhone());

        String passwordHashred = BCrypt.withDefaults().hashToString(12, newUser.getPassword().toCharArray());

        newUser.setPassword(passwordHashred);

        newUser = this.service.save(newUser);

        return ResponseEntity.ok().body(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User obj) {
        User userVerify = this.service.findByEmail(obj.getEmail());

        if (userVerify == null || userVerify.getDeleted()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não cadastrado!");
        }

        var passwordVerify = BCrypt.verifyer().verify(obj.getPassword().toCharArray(), userVerify.getPassword());

        if (Boolean.FALSE.equals(passwordVerify.verified))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Senha incorreta!");

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userVerify);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<User> get(@PathVariable Long id) {
        User user = this.service.findById(id);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody UserDTO obj) {
        User currentUser = this.service.findById(id);

        this.service.verifyPassword(obj.getPassword(), currentUser.getPassword());

        if (obj.getNewPassword() != null) {
            String newPassword = obj.getNewPassword();

            this.service.verifyNewAndCurrentPassword(newPassword, obj.getPassword());

            currentUser.setPassword(BCrypt.withDefaults().hashToString(12, newPassword.toCharArray()));
        } else {
            this.service.verifyEmail(obj.getEmail());
            this.service.verifyPhone(obj.getPhone());
        }

        User nonNullUser = this.service.notNull(currentUser, obj);

        User updatedUser = this.service.save(nonNullUser);

        return ResponseEntity.ok().body(updatedUser);
    }

    @PostMapping("/add-wish/{id}")
    public ResponseEntity<User> addWish(@PathVariable Long id, @RequestBody List<Long> idProducts) {
        User user = this.service.findById(id);

        for (Long idProduct : idProducts) {
            Product product = this.productService.findById(idProduct);
            user.getWishes().add(product);
            this.productService.save(product);
        }
        user = this.service.save(user);

        return ResponseEntity.ok().body(user);
    }
}
