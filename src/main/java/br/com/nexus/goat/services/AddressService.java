package br.com.nexus.goat.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.nexus.goat.entity.Address;
import br.com.nexus.goat.exceptions.IncompleteDataException;
import br.com.nexus.goat.exceptions.NotFoundException;
import br.com.nexus.goat.repositories.AddressRepository;

@Service
public class AddressService {

    @Autowired
    private AddressRepository repository;

    @Transactional
    public Address findById(Long id) {
        return this.repository.findById(id).orElseThrow(() -> new NotFoundException("Endereço"));
    }

    @Transactional
    public List<Address> findAllByUserId(Long idUser) {
        return this.repository.findAllByUserId(idUser).orElseThrow(() -> new NotFoundException("Endereço"));
    }

    @Transactional
    public Address save(Address address) {
        try {
            return this.repository.save(address);
        } catch (Exception e) {
            throw new IncompleteDataException();
        }
    }

    @Transactional
    public void deleteById(Long id) {
        this.repository.findById(id).orElseThrow(() -> new NotFoundException("Endereço"));
        this.repository.deleteById(id);
    }
}
