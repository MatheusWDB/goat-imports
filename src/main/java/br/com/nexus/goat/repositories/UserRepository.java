package br.com.nexus.goat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nexus.goat.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByPhone(String phone);
}
