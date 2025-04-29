package br.com.nexus.goat.exceptions.user;

public class UserDeletedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UserDeletedException() {
        super("Usuário deletado!");
    }
}
