package br.com.nexus.goat.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nexus.goat.exceptions.IncompleteDataException;
import br.com.nexus.goat.exceptions.NotFoundException;
import br.com.nexus.goat.models.Address;
import br.com.nexus.goat.repositories.AddressRepository;

@Service
public class AddressService {

    @Autowired
    private AddressRepository repository;

    public Address findById(Long id) {
        return this.repository.findById(id).orElseThrow(() -> new NotFoundException("Endereço"));
    }

    public List<Address> findAllByUserId(Long idUser) {
        return this.repository.findAllByUserId(idUser).orElseThrow(() -> new NotFoundException("Endereço"));
    }

    public Address save(Address address) {
        try {
            return this.repository.save(address);
        } catch (Exception e) {
            throw new IncompleteDataException();
        }
    }

    public void deleteById(Long id) {
        this.repository.findById(id).orElseThrow(() -> new NotFoundException("Endereço"));
        this.repository.deleteById(id);
    }
}
