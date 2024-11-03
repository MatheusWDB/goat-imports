package br.com.nexus.goat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nexus.goat.models.User;
import br.com.nexus.goat.models.dto.UserDTO;
import br.com.nexus.goat.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public Boolean verifyEmail(String email) {
        User verification = this.repository.findByEmail(email);
        if (verification != null)
            return false;
        return true;
    }

    public Boolean verifyPhone(String phone) {
        User verification = this.repository.findByPhone(phone);
        if (verification != null)
            return false;
        return true;
    }

    public User notNull(User user, UserDTO updatedUser){
        if (updatedUser.getName() != null){
            user.setName(updatedUser.getName());
        }
        if (updatedUser.getSurname() != null){
            user.setSurname(updatedUser.getSurname());
        }
        if (updatedUser.getEmail() != null){
            user.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getPhone() != null){
            user.setPhone(updatedUser.getPhone());
        }
        if (updatedUser.getDateForBirth() != null){
            user.setDateForBirth(updatedUser.getDateForBirth());
        }

        return user;
    }
}
