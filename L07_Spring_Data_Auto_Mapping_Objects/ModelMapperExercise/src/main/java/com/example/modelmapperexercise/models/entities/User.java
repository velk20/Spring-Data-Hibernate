package com.example.modelmapperexercise.models.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false)
    private String fullName;
    @Column(nullable = false)
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Game> games;
    private boolean isAdmin;
    @OneToMany(targetEntity = Order.class,mappedBy = "buyer")
    private Set<Order> orders;

    public User() {
        this.games = new HashSet<>();
        this.orders = new HashSet<>();
    }

    public User(String email, String fullName, String password) {
        this();
        this.email = email;
        this.fullName = fullName;
        this.password = password;
    }

    public User(String email, String fullName, String password, boolean isAdmin) {
        this();
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public void addAllGames(Set<Game> games) {
        this.getGames().addAll(games);
    }
}
