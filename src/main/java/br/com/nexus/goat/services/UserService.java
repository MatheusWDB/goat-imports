package br.com.nexus.goat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nexus.goat.exceptions.IncompleteDataException;
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
        User user = (User) this.repository.findByEmail(email);
        if(user == null){
            throw new UserNotFoundException();
        }
        return user;
    }

    public void verifyEmail(String email) {
        User user = (User) this.repository.findByEmail(email);
        if (user != null) {
            throw new UserAlreadyExistsException("O email: " + email);
        }
    }

    public void verifyPhone(String phone) {
        User user = this.repository.findByPhone(phone);
        if (user != null) {
            throw new UserAlreadyExistsException("O telefone: " + phone);
        }
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
