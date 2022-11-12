package com.example.modelmapperexercise.models.dtos;

import com.example.modelmapperexercise.validators.GameValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class AddGameDTO {
    private String title;
    private BigDecimal price;
    private double size;
    private String imageUrl;
    private String trailer;
    private LocalDate releaseDate;
    private String description;

    public AddGameDTO(String title, BigDecimal price, double size, String imageUrl, String trailer, LocalDate releaseDate, String description) {
        this.setTitle(title);
        this.setPrice(price);
        this.setSize(size);
        this.setImageUrl(imageUrl);
        this.setTrailer(trailer);
        this.setReleaseDate(releaseDate);
        this.setDescription(description);
    }

    public void setTitle(String title) {
        if (!GameValidator.validateTitle(title)) {
            throw new IllegalArgumentException("Invalid title!");
        }
        this.title = title;
    }

    public void setPrice(BigDecimal price) {
        if (!GameValidator.validatePrice(price)) {
            throw new IllegalArgumentException("Invalid price!");
        }
        this.price = price;
    }

    public void setSize(double size) {
        if (!GameValidator.validateSize(size)) {
            throw new IllegalArgumentException("Invalid size!");
        }
        this.size = size;
    }

    public void setImageUrl(String imageUrl) {
        if (!GameValidator.validateThumbnailURL(imageUrl)) {
            throw new IllegalArgumentException("Invalid imageUrl!");
        }
        this.imageUrl = imageUrl;
    }

    public void setTrailer(String trailer) {
        if (!GameValidator.validateTrailer(trailer)) {
            throw new IllegalArgumentException("Invalid trailer!");
        }
        this.trailer = trailer;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setDescription(String description) {
        if (!GameValidator.validateDescription(description)) {
            throw new IllegalArgumentException("Invalid description!");
        }
        this.description = description;
    }
}
