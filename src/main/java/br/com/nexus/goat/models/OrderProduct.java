package br.com.nexus.goat.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.nexus.goat.models.pk.OrderProductPK;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity(name = "tb_order_products")
public class OrderProduct implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private OrderProductPK id = new OrderProductPK();

    private Integer quantity;

    public OrderProduct(){
    }

    public OrderProduct(Order order, Product product, Integer quantity) {
        id.setOrder(order);
        id.setProduct(product);
        this.quantity = quantity;
    }

    @JsonIgnore
    public Order getOrder() {
        return id.getOrder();
    }

    public void setOrder(Order order) {
        id.setOrder(order);
    }

    @JsonIgnore
    public Product getProduct() {
        return id.getProduct();
    }

    public Long getIdProduct(){
        return id.getProduct().getId();
    }

    public void setProduct(Product product) {
        id.setProduct(product);
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getSubTotal(){
        return quantity * this.getProduct().getPrice();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OrderProduct other = (OrderProduct) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "OrderProduct [id_Order=" + id.getOrder().getId() + ", id_Product=" + id.getProduct().getId() + ", quantity=" + quantity + "]";
    }     
}
