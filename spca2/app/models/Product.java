package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
public class Product {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    public UUID id;

    public String name;
    public String manufacturer;
    public int stockLevel;
    public String category;

    public int price;

    @ManyToOne
    @JsonIgnoreProperties("purchaseHistory")
    public User user;

    @OneToMany (fetch = FetchType.EAGER)
    Set<Reviews> reviews;

    public Product() {
    }

    public Product(String name, String manufacturer, int stockLevel, String category, int price, User user, Set<Reviews> reviews) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.stockLevel = stockLevel;
        this.category = category;
        this.price = price;
        this.user = user;
        this.reviews = reviews;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getStockLevel() {
        return stockLevel;
    }

    public void setStockLevel(int stockLevel) {
        this.stockLevel = stockLevel;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Reviews> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Reviews> reviews) {
        this.reviews = reviews;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ProductCommand{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", stockLevel=" + stockLevel +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", user=" + user +
                ", reviews=" + reviews +
                '}';
    }
}
