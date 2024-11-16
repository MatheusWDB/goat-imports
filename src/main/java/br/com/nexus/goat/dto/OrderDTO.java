package br.com.nexus.goat.dto;

import java.util.Set;

public class OrderDTO {

    private Set<Items> items;

    public static class Items {
        private Long idProduct;
        private Integer quantity;

        public Long getIdProduct() {
            return idProduct;
        }

        public Integer getQuantity() {
            return quantity;
        }
    }

    public Set<Items> getItems() {
        return items;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((items == null) ? 0 : items.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        OrderDTO other = (OrderDTO) obj;
        if (items == null) {
            if (other.items != null)
                return false;
        } else if (!items.equals(other.items))
            return false;
        return true;
    }
}
