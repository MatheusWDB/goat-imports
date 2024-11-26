package br.com.nexus.goat.dto;

import java.math.BigDecimal;

public class PaymentDTO {

    private BigDecimal transactionAmount;
    private String token;
    private String issuerId;
    private String paymentMethodId;
    private Integer installments;
    private String description;
    private Payer payer;

    public static class Payer {
        private String email;
        private String firstName;
        private String lastName;
        private Identification identification;

        public static class Identification {
            private String type;
            private String number;

            public String getType() {
                return type;
            }

            public String getNumber() {
                return number;
            }
        }

        public String getEmail() {
            return email;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public Identification getIdentification() {
            return identification;
        }
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public String getToken() {
        return token;
    }

    public String getIssuerIid() {
        return issuerId;
    }

    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    public Integer getInstallments() {
        return installments;
    }

    public String getDescription() {
        return description;
    }

    public Payer getPayer() {
        return payer;
    }
}
