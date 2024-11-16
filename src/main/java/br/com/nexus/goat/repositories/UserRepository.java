package br.com.nexus.goat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.nexus.goat.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByEmail(String email);

    User findByPhone(String phone);
}
