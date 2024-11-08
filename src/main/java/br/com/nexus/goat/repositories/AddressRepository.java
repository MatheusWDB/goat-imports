package br.com.nexus.goat.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nexus.goat.entities.Address;


public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAllByUserId(Long idUser);
}
