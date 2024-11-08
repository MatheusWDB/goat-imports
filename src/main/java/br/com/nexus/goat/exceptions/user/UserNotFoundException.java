package br.com.nexus.goat.exceptions.user;

public class UserNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UserNotFoundException() {
        super("Usuário não cadastrado!");
    }    
}
