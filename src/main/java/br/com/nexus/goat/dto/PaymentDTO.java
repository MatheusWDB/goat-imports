package br.com.nexus.goat.dto;

import java.math.BigDecimal;

public class PaymentDTO {

    private BigDecimal transaction_amount;
    private String token;
    private String issuer_id;
    private String payment_method_id;
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

    public BigDecimal getTransaction_amount() {
        return transaction_amount;
    }

    public String getToken() {
        return token;
    }

    public String getIssuer_id() {
        return issuer_id;
    }

    public String getPayment_method_id() {
        return payment_method_id;
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
