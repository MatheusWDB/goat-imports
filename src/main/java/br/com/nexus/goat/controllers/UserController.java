package br.com.nexus.goat.controllers;

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
import br.com.nexus.goat.models.User;
import br.com.nexus.goat.models.dto.UserDTO;
import br.com.nexus.goat.repositories.UserRepository;
import br.com.nexus.goat.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public ResponseEntity<?> create(@RequestBody User newUser) {
        Boolean emailVerify = this.service.verifyEmail(newUser.getEmail());
        if (!emailVerify)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("E-mail já cadastrado");

        Boolean phoneVerify = this.service.verifyPhone(newUser.getPhone());
        if (!phoneVerify)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Telefone já cadastrado");

        String passwordHashred = BCrypt.withDefaults().hashToString(12, newUser.getPassword().toCharArray());

        newUser.setPassword(passwordHashred);

        User userCreated = this.repository.save(newUser);

        return ResponseEntity.ok().body(this.repository.save(userCreated));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User obj) {
        var userVerify = this.repository.findByEmail(obj.getEmail());

        if (userVerify == null || userVerify.getDeleted() == true) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não cadastrado!");
        }

        var passwordVerify = BCrypt.verifyer().verify(obj.getPassword().toCharArray(), userVerify.getPassword());
        if (!passwordVerify.verified)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Senha incorreta!");

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userVerify);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> userById(@PathVariable Long id) {
        User user = this.repository.findById(id).orElse(null);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UserDTO obj) {
        User currentUser = this.repository.findById(id).orElse(null);

        var passwordVerify = BCrypt.verifyer().verify(obj.getPassword().toCharArray(), currentUser.getPassword());
        if (!passwordVerify.verified)
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

        User updatedUser = this.repository.save(nonNullUser);

        return ResponseEntity.ok().body(updatedUser);
    }
}
