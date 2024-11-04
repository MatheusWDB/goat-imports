package br.com.nexus.goat.models.dto;

import br.com.nexus.goat.models.User;

public class UserDTO extends User {
    private String newPassword;

    public UserDTO() {
    }

    public UserDTO(Long id, String name, String surname, String email, String phone, String dateForBirth,
            String password, String newPassword) {
        super(id, name, surname, email, phone, dateForBirth, password);
        this.newPassword = newPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
