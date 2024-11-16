package br.com.nexus.goat.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nexus.goat.dto.TokenDTO;
import br.com.nexus.goat.dto.UserDTO;
import br.com.nexus.goat.dto.UserPutDTO;
import br.com.nexus.goat.entity.Product;
import br.com.nexus.goat.entity.User;
import br.com.nexus.goat.jwt.TokenService;
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
    public ResponseEntity<Void> register(@RequestBody @Valid User body) {
        this.service.verifyEmail(body.getEmail());
        this.service.verifyPhone(body.getPhone());

        String encryptedPassword = new BCryptPasswordEncoder().encode(body.getPassword());
        body.setPassword(encryptedPassword);

        this.service.save(body);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody @Valid User body) {
        UsernamePasswordAuthenticationToken emailPassword = new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword());
        Authentication auth = authenticationManager.authenticate(emailPassword);
        TokenDTO token = new TokenDTO(jwtUtil.generateToken((User) auth.getPrincipal()));

        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        User result = this.service.findById(id);
        UserDTO user = new UserDTO(result);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody UserPutDTO body) {
        User currentUser = this.service.findById(id);

        var emailPassword = new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword());
        authenticationManager.authenticate(emailPassword);

        if (body.getNewPassword() != null) {
            String newPassword = body.getNewPassword();

            this.service.verifyNewAndCurrentPassword(newPassword, body.getPassword());

            currentUser.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        } else {
            this.service.verifyEmail(body.getEmail());
            this.service.verifyPhone(body.getPhone());
        }

        User nonNullUser = this.service.notNull(currentUser, body);

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
