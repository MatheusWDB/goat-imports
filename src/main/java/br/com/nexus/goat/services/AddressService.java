package br.com.nexus.goat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nexus.goat.models.Address;
import br.com.nexus.goat.repositories.AddressRepository;

@Service
public class AddressService {

    @Autowired
    private AddressRepository repository;

}
