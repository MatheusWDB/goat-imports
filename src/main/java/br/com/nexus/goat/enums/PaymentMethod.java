package br.com.nexus.goat.enums;

public enum PaymentMethod {
    BANK_TRANSFER(1),
    CREDIT_CARD(2),
    DEBIT_CARD(3),
    TICKET(4);

    private int code;

    private PaymentMethod(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static PaymentMethod valueOf(int code) {
        for (PaymentMethod value : PaymentMethod.values()) {
            if (value.getCode() == code)
                return value;
        }
        throw new IllegalArgumentException("Status inválido");
    }
}
