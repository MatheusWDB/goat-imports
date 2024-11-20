package br.com.nexus.goat.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;

import br.com.nexus.goat.entities.Product;
import br.com.nexus.goat.entities.User;

public class UserDTO {
    
    private String name;
    private String surname;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private String password;
    private Set<Product> wishes = new HashSet<>();

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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Long> getWishes(){
        Set<Long> idProducts = new HashSet<>();
        for (Product product : wishes) {
            idProducts.add(product.getId());
        }
        return idProducts;
    }

    public void setWishes(Set<Product> wishes) {
        this.wishes = wishes;
    }
}
