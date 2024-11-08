package br.com.nexus.goat.exceptions.product;

public class ProductNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ProductNotFoundException() {
        super("Produto não existe!");
    }     
}
