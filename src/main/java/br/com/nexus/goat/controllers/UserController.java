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
    public ResponseEntity<?> create(@RequestBody User newUser) {
        Boolean emailVerify = this.service.verifyEmail(newUser.getEmail());
        if (emailVerify == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("E-mail já cadastrado");

        Boolean phoneVerify = this.service.verifyPhone(newUser.getPhone());
        if (phoneVerify == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Telefone já cadastrado");

        String passwordHashred = BCrypt.withDefaults().hashToString(12, newUser.getPassword().toCharArray());

        newUser.setPassword(passwordHashred);

        User userCreated = this.service.save(newUser);

        return ResponseEntity.ok().body(userCreated);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User obj) {
        User userVerify = this.service.findByEmail(obj.getEmail());

        if (userVerify == null || userVerify.getDeleted()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não cadastrado!");
        }

        var passwordVerify = BCrypt.verifyer().verify(obj.getPassword().toCharArray(), userVerify.getPassword());

        if (passwordVerify.verified == false)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Senha incorreta!");

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userVerify);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<User> get(@PathVariable Long id) {
        User user = this.service.findById(id);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UserDTO obj) {
        User currentUser = this.service.findById(id);

        if (currentUser == null)
            return ResponseEntity.badRequest().body(null);

        var passwordVerify = BCrypt.verifyer().verify(obj.getPassword().toCharArray(), currentUser.getPassword());
        if (passwordVerify.verified == false)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Senha incorreta!");

        if (obj.getNewPassword() != null) {
            String newPassword = obj.getNewPassword();

            if (newPassword.equals(obj.getPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A nova senha não pode ser igual a atual!");
            }
            currentUser.setPassword(BCrypt.withDefaults().hashToString(12, newPassword.toCharArray()));
        } else {
            Boolean emailVerify = this.service.verifyEmail(obj.getEmail());
            if (!emailVerify)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("E-mail já cadastrado");

            Boolean phoneVerify = this.service.verifyPhone(obj.getPhone());
            if (!phoneVerify)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Telefone já cadastrado");
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
