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
        private String first_name;
        private String last_name;
        private Address address;
        private Identification identification;

        public static class Address {
            private String zip_code;
            private String street_name;
            private String street_number;
            private String neighborhood;
            private String city;
            private String federal_unit;

            public String getZip_code() {
                return zip_code;
            }

            public String getStreet_name() {
                return street_name;
            }

            public String getStreet_number() {
                return street_number;
            }

            public String getNeighborhood() {
                return neighborhood;
            }

            public String getCity() {
                return city;
            }

            public String getFederal_unit() {
                return federal_unit;
            }

            @Override
            public String toString() {
                return "Address [zip_code=" + zip_code + ", street_name=" + street_name + ", street_number="
                        + street_number + ", neighborhood=" + neighborhood + ", city=" + city + ", federal_unit="
                        + federal_unit + "]";
            }
        }

        public static class Identification {
            private String type;
            private String number;

            public String getType() {
                return type;
            }

            public String getNumber() {
                return number;
            }

            @Override
            public String toString() {
                return "Identification [type=" + type + ", number=" + number + "]";
            }
        }

        public String getEmail() {
            return email;
        }

        public String getFirst_name() {
            return first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public Address getAddress() {
            return address;
        }
        
        public Identification getIdentification() {
            return identification;
        }

        @Override
        public String toString() {
            return "Payer [email=" + email + ", first_name=" + first_name + ", last_name=" + last_name + ", address="
                    + address + ", identification=" + identification + "]";
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

    @Override
    public String toString() {
        return "PaymentDTO [transaction_amount=" + transaction_amount + ", token=" + token + ", issuer_id=" + issuer_id
                + ", payment_method_id=" + payment_method_id + ", installments=" + installments + ", description="
                + description + ", payer=" + payer + "]";
    }    
}
