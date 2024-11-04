package br.com.nexus.goat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nexus.goat.models.Address;
import br.com.nexus.goat.models.User;
import br.com.nexus.goat.repositories.AddressRepository;
import br.com.nexus.goat.repositories.UserRepository;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressRepository repository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/{id_user}")
    public ResponseEntity<?> create(@PathVariable Long id_user, @RequestBody Address obj) {
        User user = userRepository.findById(id_user).orElse(null);

        obj.setUser(user);
        obj = repository.save(obj);

        return ResponseEntity.ok().body(obj);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        this.repository.deleteById(id);

        return ResponseEntity.ok().body("O endereço foi deletado");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id){
        Address address = this.repository.findById(id).orElse(null);

        return ResponseEntity.ok().body(address);
    }
}
