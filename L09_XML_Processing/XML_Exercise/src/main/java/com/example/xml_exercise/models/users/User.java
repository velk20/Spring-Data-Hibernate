package com.example.xml_exercise.models.users;

import com.example.xml_exercise.models.products.Product;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name",nullable = false)
    private String lastName;
    @Column
    private Integer age;
    @OneToMany(mappedBy = "seller",targetEntity = Product.class)
    private Set<Product> soldProducts;
    @OneToMany(mappedBy = "buyer",targetEntity = Product.class)
    private Set<Product> boughtProducts;
    @OneToMany
    private Set<User> friends;

    public User() {
        this.soldProducts = new HashSet<>();
        this.boughtProducts = new HashSet<>();
        this.friends = new HashSet<>();
    }

    public User(String firstName, String lastName, Integer age) {
        this();
        this.firstName = firstName;
        this.setLastName(lastName);
        this.age = age;
    }

    public void setLastName(String lastName) {
        if (lastName.length() < 3) {
            return;
        }
        this.lastName = lastName;
    }
}
