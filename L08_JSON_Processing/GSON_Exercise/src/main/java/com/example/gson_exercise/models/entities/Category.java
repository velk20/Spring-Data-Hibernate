package com.example.gson_exercise.models.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String name;
    @ManyToMany(mappedBy = "categories",targetEntity = Product.class)
    private Set<Product> products;

    public Category() {
        this.products = new HashSet<>();
    }

    public Category(String name) {
        this();
        this.setName(name);
    }

    public void setName(String name) {
        if (name.length() < 3 || name.length() > 15) {
            return;
        }
        this.name = name;
    }
}
