package br.com.nexus.goat.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.nexus.goat.dto.UserDTO;
import br.com.nexus.goat.entities.User;
import br.com.nexus.goat.exceptions.IncompleteDataException;
import br.com.nexus.goat.exceptions.user.UserAlreadyExistsException;
import br.com.nexus.goat.exceptions.user.UserDeletedException;
import br.com.nexus.goat.exceptions.user.UserNotFoundException;
import br.com.nexus.goat.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
        Hibernate.initialize(user.getWishes());
        if (user.getDeleted()) {
            throw new UserDeletedException();
        }
        return user;
    }

    @Transactional
    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user.getDeleted()) {
            throw new UserDeletedException();
        }
        return user;
    }

    @Transactional
    public void save(User user) {
        try {
            userRepository.save(user);
        } catch (IncompleteDataException e) {
            throw new IncompleteDataException();
        }
    }

    @Transactional
    public void verifyEmail(String email) {
        User user = (User) userRepository.findByEmail(email);
        if (user != null) {
            throw new UserAlreadyExistsException("O email: " + email);
        }
    }

    @Transactional
    public void verifyPhone(String phone) {
        User user = userRepository.findByPhone(phone);
        if (user != null) {
            throw new UserAlreadyExistsException("O telefone: " + phone);
        }
    }

    /*
     * public void verifyNewAndCurrentPassword(String newPassword, String
     * currentPassword) {
     * if (newPassword.equals(currentPassword)) {
     * throw new SamePasswordException();
     * }
     * }
     */
    public User notNull(User user, UserDTO updatedUser) {
        if (updatedUser.getName() != null) {
            user.setName(updatedUser.getName());
        }
        if (updatedUser.getSurname() != null) {
            user.setSurname(updatedUser.getSurname());
        }
        if (updatedUser.getEmail() != null) {
            if (!updatedUser.getEmail().equals(user.getEmail())) {
                this.verifyEmail(updatedUser.getEmail());
            }
            user.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getPhone() != null) {
            if (!updatedUser.getPhone().equals(user.getPhone())) {
                this.verifyPhone(updatedUser.getPhone());
            }
            user.setPhone(updatedUser.getPhone());
        }
        if (updatedUser.getDateOfBirth() != null) {
            user.setDateOfBirth(updatedUser.getDateOfBirth());
        }

        return user;
    }
}
