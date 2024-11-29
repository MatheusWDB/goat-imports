package br.com.nexus.goat.dto;

import java.util.List;

import br.com.nexus.goat.enums.OrderStatus;
import br.com.nexus.goat.enums.PaymentMethod;

public class OrderDTO {

    private List<Items> items;
    private Integer paymentMethod;
    private Integer status;

    public static class Items {
        private Long idProduct;
        private Integer quantity;
        private String size;

        public Long getIdProduct() {
            return idProduct;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public String getSize() {
            return size;
        }
    }

    public List<Items> getItems() {
        return items;
    }

    public PaymentMethod getPaymentMethod() {
        return PaymentMethod.valueOf(paymentMethod);
    }

    public OrderStatus getStatus() {
        return OrderStatus.valueOf(status);
    }
}
