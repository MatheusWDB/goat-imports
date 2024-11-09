package br.com.nexus.goat.exceptions.user;

public class SamePasswordException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public SamePasswordException() {
        super("A nova senha deve ser diferente da senha atual!");
    }
}
