package br.com.nexus.goat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.nexus.goat.exceptions.IncompleteDataException;
import br.com.nexus.goat.exceptions.user.IncorrectPasswordException;
import br.com.nexus.goat.exceptions.user.SamePasswordException;
import br.com.nexus.goat.exceptions.user.UserAlreadyExistsException;
import br.com.nexus.goat.exceptions.user.UserDeletedException;
import br.com.nexus.goat.exceptions.user.UserNotFoundException;
import br.com.nexus.goat.models.User;
import br.com.nexus.goat.models.dto.UserDTO;
import br.com.nexus.goat.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User findById(Long id) {
        User user = this.repository.findById(id).orElseThrow(() -> new UserNotFoundException());
        if (user.getDeleted()) {
            throw new UserDeletedException();
        }
        return user;
    }

    public User save(User user) {
        try {
            return this.repository.save(user);
        } catch (IncompleteDataException e) {
            throw new IncompleteDataException();
        }
    }

    public User findByEmail(String email) {
        return this.repository.findByEmail(email).orElseThrow(() -> new UserNotFoundException());
    }

    public void verifyPassword(String newPassword, String currentPassword) {
        var passwordVerify = BCrypt.verifyer().verify(newPassword.toCharArray(), currentPassword);
        if (Boolean.FALSE.equals(passwordVerify.verified)) {
            throw new IncorrectPasswordException();
        }
    }

    public void verifyEmail(String email) {
        this.repository.findByEmail(email)
                .orElseThrow(() -> new UserAlreadyExistsException("Já existe um usuário com esse e-mail!"));
    }

    public void verifyPhone(String phone) {
        this.repository.findByPhone(phone)
                .orElseThrow(() -> new UserAlreadyExistsException("Já existe um usuário com esse telefone!"));
    }

    public void verifyNewAndCurrentPassword(String newPassword, String currentPassword) {
        if (newPassword.equals(currentPassword)) {
            throw new SamePasswordException();
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
