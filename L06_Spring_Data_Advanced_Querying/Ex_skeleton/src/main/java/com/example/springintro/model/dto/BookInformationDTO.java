package com.example.springintro.model.dto;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.EditionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class BookInformationDTO {
    private String title;
    private EditionType editionType;
    private AgeRestriction ageRestriction;
    private BigDecimal price;

    @Override
    public String toString() {
        return String.format("%s %s %s %s",
                getTitle(),
                getEditionType().name(),
                getAgeRestriction().name(),
                getPrice());
    }
}
