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
import br.com.nexus.goat.dto.IdDTO;
import br.com.nexus.goat.dto.UserDTO;
import br.com.nexus.goat.entities.Product;
import br.com.nexus.goat.entities.User;
import br.com.nexus.goat.exceptions.user.IncorrectPasswordException;
import br.com.nexus.goat.services.ProductService;
import br.com.nexus.goat.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody User body) {
        userService.verifyEmail(body.getEmail());
        userService.verifyPhone(body.getPhone());

        String bcryptHashString = BCrypt.withDefaults().hashToString(12, body.getPassword().toCharArray());

        body.setPassword(bcryptHashString);

        userService.save(body);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/login")
    public ResponseEntity<IdDTO> login(@RequestBody User body) {
        User user = userService.findByEmail(body.getEmail());

        BCrypt.Result result = BCrypt.verifyer().verify(body.getPassword().toCharArray(), user.getPassword());

        if (!result.verified) {
            throw new IncorrectPasswordException();
        }

        IdDTO id = new IdDTO(user.getId());

        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        User result = userService.findById(id);
        UserDTO user = new UserDTO(result);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody UserDTO body) {
        User user = userService.findById(id);

        BCrypt.Result result = BCrypt.verifyer().verify(body.getPassword().toCharArray(), user.getPassword());

        if (!result.verified) {
            throw new IncorrectPasswordException();
        }

        User nonNullUser = userService.notNull(user, body);

        userService.save(nonNullUser);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/add-wish/{id}")
    public ResponseEntity<Void> addWish(@PathVariable Long id, @RequestBody Set<Long> body) {
        User user = userService.findById(id);

        System.out.println("AQUIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");
        for (Long idProduct : body) {
            Product product = productService.findById(idProduct);
            user.getWishes().add(product);
            productService.save(product);
        }
        userService.save(user);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove-wish/{id}")
    public ResponseEntity<Void> removeWish(@PathVariable Long id, @RequestBody Set<Long> body) {
        User user = userService.findById(id);
        for (Long idProduct : body) {
            Product product = productService.findById(idProduct);
            user.getWishes().remove(product);
            productService.save(product);
        }
        userService.save(user);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<Void> softDelete(@PathVariable Long id, @RequestBody String body) {
        User user = userService.findById(id);

        BCrypt.Result result = BCrypt.verifyer().verify(body.toCharArray(), user.getPassword());

        if (!result.verified) {
            throw new IncorrectPasswordException();
        }

        user.setDeleted(true);
        userService.save(user);

        return ResponseEntity.ok().build();
    }
}
