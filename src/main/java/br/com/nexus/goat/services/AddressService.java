package br.com.nexus.goat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nexus.goat.entities.Address;
import br.com.nexus.goat.repositories.AddressRepository;

@Service
public class AddressService {

    @Autowired
    private AddressRepository repository;

    public Address findById(Long id){
        return this.repository.findById(id).orElse(null);
    }

    public Address save(Address address){
        address = this.repository.save(address);
        return address;
    }

    public void deleteById(Long id){
        this.repository.deleteById(id);
    }
}
