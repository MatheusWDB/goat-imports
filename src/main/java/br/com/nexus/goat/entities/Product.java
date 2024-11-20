package br.com.nexus.goat.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity(name = "tb_products")
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer stock;

    private String imgUrl;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy'T'HH:mm:ss", timezone = "GMT-03:00")
    private Instant createdAt;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "tb_product_categories", joinColumns = @JoinColumn(name = "id_product"), inverseJoinColumns = @JoinColumn(name = "id_category"))
    private Set<Category> categories = new HashSet<>();
    
    @OneToOne
    @JoinColumn(name = "id_features", referencedColumnName = "id")
    private Feature features;

    @JsonIgnore
    @OneToMany(mappedBy = "id.product")
    private Set<OrderProduct> products = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "wishes")
    private Set<User> wishes = new HashSet<>();

    public Product() {
    }

    public Product(String name, String description, Double price, Integer stock, String imgUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.imgUrl = imgUrl;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public Feature getFeatures() {
        return features;
    }

    public void setFeatures(Feature features) {
        this.features = features;
    }

    public Set<User> getWishes() {
        return wishes;
    }

    public Set<Order> getProducts() {
        Set<Order> set = new HashSet<>();
        for (OrderProduct x : products) {
            set.add(x.getOrder());
        }
        return set;
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
        Product other = (Product) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
