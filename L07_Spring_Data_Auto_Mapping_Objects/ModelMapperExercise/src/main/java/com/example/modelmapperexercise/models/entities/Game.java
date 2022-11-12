package com.example.modelmapperexercise.models.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "games")
@Getter
@Setter
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false,unique = true)
    private String title;
    @Column(nullable = false)
    private String trailer;
    @Column(nullable = false)
    private String imageUrl;

    private double size;
    @Column(nullable = false)
    private BigDecimal price;
    private String description;
    @Column(nullable = false)
    private LocalDate releaseDate;

    public Game() {
    }

    public Game(String title, String trailer, String imageUrl, double size, BigDecimal price, String description, LocalDate releaseDate) {
        this.title = title;
        this.trailer = trailer;
        this.imageUrl = imageUrl;
        this.size = size;
        this.price = price;
        this.description = description;
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Title: ").append(this.getTitle());
        sb.append(System.lineSeparator());
        sb.append(System.lineSeparator());
        sb.append("Price: ").append(this.getPrice());
        sb.append(System.lineSeparator());
        sb.append(System.lineSeparator());
        sb.append("Description: ").append(this.getDescription());
        sb.append(System.lineSeparator());
        sb.append(System.lineSeparator());
        sb.append("Release date: ").append(this.getReleaseDate());

        return sb.toString();
    }
}
