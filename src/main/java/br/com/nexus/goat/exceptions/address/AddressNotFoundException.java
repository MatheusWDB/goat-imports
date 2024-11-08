package br.com.nexus.goat.exceptions.address;

public class AddressNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AddressNotFoundException() {
        super("Endereço não existe!");
    }    
}
