package br.com.nexus.goat.exceptions.user;

public class IncorrectPasswordException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public IncorrectPasswordException() {
        super("Senha incorreta!");
    }
    
}
