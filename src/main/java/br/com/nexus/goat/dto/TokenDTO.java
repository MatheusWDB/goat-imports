package br.com.nexus.goat.dto;

public class TokenDTO {

    private String token;

    public void setToken(String token) {
        this.token = token;
    }

    public TokenDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
