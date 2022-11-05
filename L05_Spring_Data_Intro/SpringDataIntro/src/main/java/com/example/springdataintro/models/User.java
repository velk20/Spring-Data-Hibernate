package com.example.springdataintro.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Basic
    private int age;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Account> accountList;

    public User() {
        accountList = new ArrayList<>();
    }

    public User(String username, int age, Account account) {
        this();
        this.username = username;
        setAge(age);
        this.accountList.add(account);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Long> getAccountIds() {
        return this.accountList
                .stream()
                .map(Account::getId)
                .collect(Collectors.toList());
    }

    public void addAccount(Account account) {
    }

    public void removeAccount(Account account) {

    }
}
