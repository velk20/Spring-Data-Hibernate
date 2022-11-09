package com.example.springintro.model.dto;

import com.example.springintro.model.entity.Author;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookCopiesDTO {
    private Author author;
    private Long copies;

    @Override
    public String toString() {
        return String.format("%s %s - %d", author.getFirstName(), author.getLastName(), copies);
    }
}
