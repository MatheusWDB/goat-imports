package br.com.nexus.goat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.nexus.goat.entities.User;
import br.com.nexus.goat.entities.dto.UserDTO;
import br.com.nexus.goat.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User findById(Long id) {
        return this.repository.findById(id).orElseThrow(null);
    }

    public User save(User user) {
        return this.repository.save(user);
    }

    public User findByEmail(String email) {
        return this.repository.findByEmail(email);
    }

    public void verifyPassword(String newPassword, String currentPassword) {
        var passwordVerify = BCrypt.verifyer().verify(newPassword.toCharArray(), currentPassword);
        if (Boolean.FALSE.equals(passwordVerify.verified)) {
            throw new IllegalArgumentException("Senha incorreta!");
        }
    }

    public void verifyEmail(String email) {
        User user = this.repository.findByEmail(email);
        if (user != null) {
            throw new DuplicateKeyException("E-mail já cadastrado");
        }
    }

    public void verifyPhone(String phone) {
        User user = this.repository.findByPhone(phone);
        if (user != null) {
            throw new DuplicateKeyException("Telefone já cadastrado");
        }
    }

    public void verifyNewAndCurrentPassword(String newPassword, String currentPassword) {
        if (newPassword.equals(currentPassword)) {
                throw new IllegalArgumentException("A nova senha não pode ser igual a atual!");
            }
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
