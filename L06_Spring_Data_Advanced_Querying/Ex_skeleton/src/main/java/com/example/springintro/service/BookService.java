package com.example.springintro.service;

import com.example.springintro.model.dto.BookInformationDTO;
import com.example.springintro.model.dto.BookSummary;
import com.example.springintro.model.entity.Book;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<Book> findAllBooksBeforeReleasedDate(LocalDate localDate);

    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);

    List<Book> findAllByAgeRestriction(String ageRestriction);

    List<Book> findGoldenBooks();

    List<Book> findAllByPriceNotBetween();

    List<Book> findNotReleasedIn(int releaseYear);

    List<Book> findAllByTitleContainingIgnoreCase(String str);

    List<Book> findAllByAuthorLastNameIgnoreCaseContaining(String str);

    Long countDistinctByTitleLength(int len);

    List<BookInformationDTO> findAllByTitle(String title);
    List<BookSummary> getBookSummary();

    Integer increaseBookCopiesAfterDate(String date, int copies);

    int removeBooksLowerThatGivenCopies(int copies);

    int getCountOfBooksByAuthor(String firstName, String lastName);

}
