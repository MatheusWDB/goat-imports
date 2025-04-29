package br.com.nexus.goat.enums;

public enum OrderStatus {    
    PENDING(1),
    APPROVED(2),
    IN_PROCCESS(3),
    REJECTED(4);

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
