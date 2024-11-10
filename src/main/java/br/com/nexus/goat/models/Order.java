package br.com.nexus.goat.models;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.nexus.goat.enums.OrderStatus;
import br.com.nexus.goat.enums.PaymentMethod;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity(name = "tb_orders")
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer status;

    private Integer paymentMethod;

    @Column(nullable = false)
    private Long orderNumber;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy'T'HH:mm:ss", timezone = "GMT-03:00")
    private Instant orderDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    @JoinColumn(name = "id_address", referencedColumnName = "id")
    private Address address;

    @OneToMany(mappedBy = "id.order")
    private Set<OrderProduct> products = new HashSet<>();

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return OrderStatus.valueOf(status);
    }

    public void setStatus(OrderStatus status) {
        if (status != null)
            this.status = status.getCode();
    }

    public PaymentMethod getPaymentMethod() {
        return PaymentMethod.valueOf(paymentMethod);
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        if (paymentMethod != null) {
            this.paymentMethod = paymentMethod.getCode();
        }
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Instant getOrderDate() {
        return orderDate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Long getIdAddress() {
        return address.getId();
    }

    public Long getIdUser() {
        return address.getIdUser();
    }

    public Set<OrderProduct> getProducts() {
        return products;
    }

    public Double getTotal() {
        double sum = 0.0;
        for (OrderProduct x : products) {
            sum += x.getSubTotal();
        }
        return sum;
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
        Order other = (Order) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ", status=" + status + ", paymentMethod=" + paymentMethod + ", orderNumber="
                + orderNumber + ", orderDate=" + orderDate + ", address=" + address + ", products=" + products + "]";
    }
}
