package br.com.nexus.goat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nexus.goat.models.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
    
}
