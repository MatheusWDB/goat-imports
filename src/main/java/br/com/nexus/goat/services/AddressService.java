package br.com.nexus.goat.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nexus.goat.entities.Address;
import br.com.nexus.goat.exceptions.address.AddressNotFoundException;
import br.com.nexus.goat.repositories.AddressRepository;

@Service
public class AddressService {

    @Autowired
    private AddressRepository repository;

    public Address findById(Long id){
        return this.repository.findById(id).orElseThrow(new AddressNotFoundException());
    }

    public List<Address> findAllByUserId(Long idUser){
        return this.repository.findAllByUserId(idUser);
    }

    public Address save(Address address){
        address = this.repository.save(address);
        return address;
    }

    public void deleteById(Long id){
        Address address = this.repository.findById(id);
        if(address == null){
            throw new AddressNotFoundException();
        }
        this.repository.deleteById(id);
    }
}
