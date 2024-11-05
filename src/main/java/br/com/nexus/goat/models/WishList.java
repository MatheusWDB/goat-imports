package br.com.nexus.goat.models;

import java.io.Serializable;
import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.nexus.goat.models.pk.WishListPK;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity(name = "tb_wish_lists")
public class WishList implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private WishListPK id = new WishListPK();

    @CreationTimestamp
    private Instant modificationDate;

    public WishList() {
    }

    public WishList(User user, Product product, Instant modificationDate) {
        id.setUser(user);
        id.setProduct(product);
        this.modificationDate = modificationDate;
    }

    @JsonIgnore
    public User getUser() {
        return id.getUser();
    }

    public void setUser(User user) {
        id.setUser(user);;
    }

    public Product getProduct() {
        return id.getProduct();
    }

    public void setProduct(Product product) {
        id.setProduct(product);
    }

    public Instant getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Instant modificationDate) {
        this.modificationDate = modificationDate;
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
        WishList other = (WishList) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "WishList [modificationDate=" + modificationDate + ", getUser()=" + getUser()
                + ", getProduct()=" + getProduct() + "]";
    }    
}
