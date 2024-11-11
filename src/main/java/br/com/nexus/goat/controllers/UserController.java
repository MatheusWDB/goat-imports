package br.com.nexus.goat.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nexus.goat.jwt.TokenService;
import br.com.nexus.goat.models.Product;
import br.com.nexus.goat.models.User;
import br.com.nexus.goat.models.dto.LoginResponseDTO;
import br.com.nexus.goat.models.dto.UserDTO;
import br.com.nexus.goat.services.ProductService;
import br.com.nexus.goat.services.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService jwtUtil;

    @Autowired
    private ProductService productService;

    @PostMapping("/register")
    public ResponseEntity<Void> create(@RequestBody @Valid User newUser) {
        this.service.verifyEmail(newUser.getEmail());
        this.service.verifyPhone(newUser.getPhone());

        String encryptedPassword = new BCryptPasswordEncoder().encode(newUser.getPassword());
        newUser.setPassword(encryptedPassword);
        
        this.service.save(newUser);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid User obj) {
        var emailPassword = new UsernamePasswordAuthenticationToken(obj.getEmail(), obj.getPassword());
        var auth = authenticationManager.authenticate(emailPassword);

        var token = jwtUtil.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<User> get(@PathVariable Long id) {
        User user = this.service.findById(id);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody UserDTO obj) {
        User currentUser = this.service.findById(id);

        var emailPassword = new UsernamePasswordAuthenticationToken(obj.getEmail(), obj.getPassword());
        authenticationManager.authenticate(emailPassword);

        if (obj.getNewPassword() != null) {
            String newPassword = obj.getNewPassword();

            this.service.verifyNewAndCurrentPassword(newPassword, obj.getPassword());

            currentUser.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        } else {
            this.service.verifyEmail(obj.getEmail());
            this.service.verifyPhone(obj.getPhone());
        }

        User nonNullUser = this.service.notNull(currentUser, obj);

        this.service.save(nonNullUser);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/add-wish/{id}")
    public ResponseEntity<Void> addWish(@PathVariable Long id, @RequestBody Set<Long> idProducts) {
        User user = this.service.findById(id);

        for (Long idProduct : idProducts) {
            Product product = this.productService.findById(idProduct);
            user.getWishes().add(product);
            this.productService.save(product);
        }
        user = this.service.save(user);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove-wish/{id}")
    public ResponseEntity<Void> removeWish(@PathVariable Long id, @RequestBody Set<Long> idProducts) {
        User user = this.service.findById(id);
        for (Long idProduct : idProducts) {
            Product product = this.productService.findById(idProduct);
            user.getWishes().remove(product);
            this.productService.save(product);
        }
        user = this.service.save(user);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<Void> fakeDelete(@PathVariable Long id) {
        User user = this.service.findById(id);
        user.setDeleted(true);
        this.service.save(user);

        return ResponseEntity.ok().build();
    }
}
