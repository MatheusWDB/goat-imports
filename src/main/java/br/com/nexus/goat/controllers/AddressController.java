package br.com.nexus.goat.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nexus.goat.entities.Address;
import br.com.nexus.goat.entities.User;
import br.com.nexus.goat.services.AddressService;
import br.com.nexus.goat.services.UserService;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserService userService;

    @PostMapping("/create/{idUser}")
    public ResponseEntity<Void> create(@PathVariable Long idUser, @RequestBody Address body) {
        User user = userService.findById(idUser);

        body.setUser(user);
        body = addressService.save(body);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        addressService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Address> findById(@PathVariable Long id) {
        Address address = addressService.findById(id);

        return ResponseEntity.ok().body(address);
    }

    @GetMapping("/findAllByUserId/{idUser}")
    public ResponseEntity<List<Address>> findAllByUserId(@PathVariable Long idUser) {
        List<Address> addresses = addressService.findAllByUserId(idUser);

        return ResponseEntity.ok().body(addresses);
    }

}
