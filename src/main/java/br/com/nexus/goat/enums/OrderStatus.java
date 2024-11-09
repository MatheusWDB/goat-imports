package br.com.nexus.goat.enums;

public enum OrderStatus {    
    PAID(1),
    SHIPPED(2),
    DELIVERED(3),
    WAITING_PAYMENT(4);

    private int code;

    private OrderStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static OrderStatus valueOf(int code) {
        for (OrderStatus value : OrderStatus.values()) {
            if (value.getCode() == code)
                return value;
        }
        throw new IllegalArgumentException("Status inválido");
    }
}
