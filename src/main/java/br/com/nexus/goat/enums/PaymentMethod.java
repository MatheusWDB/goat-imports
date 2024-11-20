package br.com.nexus.goat.enums;

public enum PaymentMethod {
    PIX(1),
    CREDIT_CARD(2),
    DEBIT_CARD(3),
    CASH(4),
    BANK_TRANSFER(5),
    PAYPAL(6);

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
