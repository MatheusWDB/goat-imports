package br.com.nexus.goat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nexus.goat.entities.User;
import br.com.nexus.goat.entities.dto.UserDTO;
import br.com.nexus.goat.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User findById(Long id) {
        return this.repository.findById(id).orElse(null);
    }

    public User save(User user) {
        return this.repository.save(user);
    }

    public User findByEmail(String email) {
        return this.repository.findByEmail(email);
    }

    public Boolean verifyEmail(String email) {
        return this.repository.findByEmail(email) == null;
    }

    public Boolean verifyPhone(String phone) {
        return this.repository.findByPhone(phone) == null;
    }

    public User notNull(User user, UserDTO updatedUser) {
        if (updatedUser.getName() != null) {
            user.setName(updatedUser.getName());
        }
        if (updatedUser.getSurname() != null) {
            user.setSurname(updatedUser.getSurname());
        }
        if (updatedUser.getEmail() != null) {
            user.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getPhone() != null) {
            user.setPhone(updatedUser.getPhone());
        }
        if (updatedUser.getDateOfBirth() != null) {
            user.setDateOfBirth(updatedUser.getDateOfBirth());
        }

        return user;
    }
}
