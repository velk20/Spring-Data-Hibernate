package com.example.xml_exercise.models.products;

import com.example.xml_exercise.models.categories.Category;
import com.example.xml_exercise.models.users.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
@ToString
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private BigDecimal price;
    @ManyToOne
    private User buyer;
    @ManyToOne( optional = false)
    private User seller;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Category> categories;

    public Product() {
        this.categories = new HashSet<>();
    }

    public Product(String name, BigDecimal price, User buyer, User seller) {
        this();
        this.setName(name);
        this.price = price;
        this.buyer = buyer;
        this.seller = seller;
    }

    public void setName(String name) {
        if (name.length() < 3) {
            return;
        }
        this.name = name;
    }
}
