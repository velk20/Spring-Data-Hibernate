package com.example.modelmapperexercise.models.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
@ToString
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(optional = false)
    private User buyer;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Game> products;

    public Order() {
        this.products = new HashSet<>();
    }

    public Order(User buyer, Set<Game> products) {
        this();
        this.buyer = buyer;
        this.products = products;
    }
}
