package entities;

import annotations.Column;
import annotations.Entity;
import annotations.Id;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Arrays;

@Entity(name="users")
public class User {
    @Id
    @Column(name="id")
    private long id;
    @Column(name="username")

    private String username;
    @Column(name="age")

    private int age;
    @Column(name="registration_date")

    private LocalDate registrationDate;

    public User(String username, int age, LocalDate registrationLocalDate) {
        this.username = username;
        this.age = age;
        this.registrationDate = registrationLocalDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public LocalDate getRegistrationLocalDate() {
        return registrationDate;
    }

    public void setRegistrationLocalDate(LocalDate registrationLocalDate) {
        this.registrationDate = registrationLocalDate;
    }
}
