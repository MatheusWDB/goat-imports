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

import br.com.nexus.goat.entities.Address;
import br.com.nexus.goat.entities.User;
import br.com.nexus.goat.services.AddressService;
import br.com.nexus.goat.services.UserService;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressService service;

    @Autowired
    private UserService userService;

    @PostMapping("/create/{idUser}")
    public ResponseEntity<Address> create(@PathVariable Long idUser, @RequestBody Address obj) {
        User user = this.userService.findById(idUser);

        obj.setUser(user);        
        obj = service.save(obj);
        
        user.getAddresses().add(obj);

        this.userService.save(user);
        return ResponseEntity.ok().body(obj);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        this.service.deleteById(id);
        return ResponseEntity.ok().body("O endereço foi deletado");
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Address> get(@PathVariable Long id) {
        Address address = this.service.findById(id);

        return ResponseEntity.ok().body(address);
    }
}
