package br.com.nexus.goat.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nexus.goat.models.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<List<Address>> findAllByUserId(Long idUser);
}
