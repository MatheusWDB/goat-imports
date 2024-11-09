package br.com.nexus.goat.exceptions;

public class NotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public NotFoundException(String r) {
        super(r + " não encontrado(a)!");
    }
}
