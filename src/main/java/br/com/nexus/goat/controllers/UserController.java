package br.com.nexus.goat.controllers;

import java.util.Set;

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
import br.com.nexus.goat.exceptions.user.IncorrectPasswordException;
import br.com.nexus.goat.models.Product;
import br.com.nexus.goat.models.User;
import br.com.nexus.goat.models.dto.UserDTO;
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
    public ResponseEntity<User> login(@RequestBody User obj) {
        User user = this.service.findByEmail(obj.getEmail());

        var passwordVerify = BCrypt.verifyer().verify(obj.getPassword().toCharArray(), user.getPassword());

        if (Boolean.FALSE.equals(passwordVerify.verified)) {
            throw new IncorrectPasswordException();
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
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
    public ResponseEntity<User> addWish(@PathVariable Long id, @RequestBody Set<Long> idProducts) {
        User user = this.service.findById(id);

        for (Long idProduct : idProducts) {
            Product product = this.productService.findById(idProduct);
            user.getWishes().add(product);
            this.productService.save(product);
        }
        user = this.service.save(user);

        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/remove-wish/{id}")
    public ResponseEntity<?> removeWish(@PathVariable Long id, @RequestBody Set<Long> idProducts) {
        User user = this.service.findById(id);
        for (Long idProduct : idProducts) {
            Product product = this.productService.findById(idProduct);
            user.getWishes().remove(product);
            this.productService.save(product);
        }
        user = this.service.save(user);
        return ResponseEntity.ok().body(null);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<String> fakeDelete(@PathVariable Long id) {
        User user = this.service.findById(id);
        user.setDeleted(true);
        this.service.save(user);

        return ResponseEntity.ok().body("Usuário deletado");
    }
}
