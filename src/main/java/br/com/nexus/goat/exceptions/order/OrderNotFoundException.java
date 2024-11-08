package br.com.nexus.goat.exceptions.order;

public class OrderNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public OrderNotFoundException() {
        super("Pedido não existe!");
    }     
}
