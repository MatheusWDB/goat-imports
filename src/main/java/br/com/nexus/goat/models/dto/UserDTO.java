package br.com.nexus.goat.models.dto;

import br.com.nexus.goat.models.User;

public class UserDTO extends User {
    private String newPassword;

    public String getNewPassword() {
        return newPassword;
    }

    @Override
    public String toString() {
        return "UserDTO [newPassword=" + newPassword + ", getName()=" + getName() + ", getSurname()=" + getSurname()
                + ", getEmail()=" + getEmail() + ", getPhone()=" + getPhone() + ", getDateForBirth()="
                + getDateForBirth() + ", getPassword()=" + getPassword() + ", getCreatedAt()=" + getCreatedAt() + "]";
    }
}
