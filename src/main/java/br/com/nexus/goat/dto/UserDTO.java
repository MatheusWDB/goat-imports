package br.com.nexus.goat.dto;

import org.springframework.beans.BeanUtils;

import br.com.nexus.goat.entity.User;

public class UserDTO {
    
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String dateOfBirth;
    private String password;

    public UserDTO() {
    }

    public UserDTO(User entity) {
        BeanUtils.copyProperties(entity, this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
