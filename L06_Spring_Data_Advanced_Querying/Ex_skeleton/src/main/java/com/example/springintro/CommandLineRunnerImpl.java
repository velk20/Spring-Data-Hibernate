package com.example.springintro;

import com.example.springintro.model.dto.BookCopiesDTO;
import com.example.springintro.model.dto.BookInformationDTO;
import com.example.springintro.model.dto.BookSummary;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.EditionType;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        _11_AllBooksByTitle("Things Fall Apart");

    }


    private void _11_AllBooksByTitle(String title) {
        List<BookInformationDTO> allByTitle = this.bookService.findAllByTitle(title);
        for (BookInformationDTO dto : allByTitle) {
            System.out.println(dto);
        }
    }
    private void _10_TotalBooksCopies() {
        List<BookCopiesDTO> dtos = this.authorService.findAllByCountCopies();
        for (BookCopiesDTO dto : dtos) {
            System.out.println(dto);
        }
    }
    private void _09_CountBooks(int len) {
        System.out.println(bookService.countDistinctByTitleLength(len));
    }

    private void _08BookTitlesSearch(String str) {
        bookService.findAllByAuthorLastNameIgnoreCaseContaining(str)
                .forEach(b -> System.out.printf("%s (%s %s)\n", b.getTitle(), b.getAuthor().getFirstName(),
                        b.getAuthor().getLastName()));
    }
    private void _07_BooksSearch(String str) {
        bookService.findAllByTitleContainingIgnoreCase(str)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }
    private void _06_AuthorsSearch(String endString) {
        authorService.findAllByFirstNameEndingWith(endString)
                .forEach(a -> System.out.printf("%s %s\n",a.getFirstName(),a.getLastName()));
    }

    private void _05_BooksReleasedBefore(String localDate) {
        String[] dates = localDate.split("-");
        LocalDate formattedLocalDate = LocalDate.of(
                Integer.parseInt(dates[2]),
                Integer.parseInt(dates[1]),
                Integer.parseInt(dates[0]));

        bookService.findAllBooksBeforeReleasedDate(formattedLocalDate)
                .forEach(b -> System.out.printf("%s %s %.2f\n",
                        b.getTitle(),
                        EditionType.values()[b.getEditionType().ordinal()],
                        b.getPrice()));
    }
    private void _04_notReleasedBooks(int year) {
        bookService.findNotReleasedIn(year)
                .stream()
                .sorted(Comparator.comparing(Book::getId))
                .map(Book::getTitle)
                .forEach(System.out::println);
    }
    private void _03_booksByPrice(){
        bookService.findAllByPriceNotBetween()
                .forEach(e -> System.out.printf("%s - %.2f\n", e.getTitle(), e.getPrice()));
    }
    private void _02_goldenBooks() {
        bookService.findGoldenBooks()
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void _01_printTitleByAgeRestriction(String age) {
        bookService.findAllByAgeRestriction(age)
                .stream().map(Book::getTitle)
                .forEach(System.out::println);

    }

    private void pritnALlBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
        bookService
                .findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(firstName, lastName)
                .forEach(System.out::println);
    }

    private void printAllAuthorsAndNumberOfTheirBooks() {
        authorService
                .getAllAuthorsOrderByCountOfTheirBooks()
                .forEach(System.out::println);
    }

    private void printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(int year) {
        bookService
                .findAllAuthorsWithBooksWithReleaseDateBeforeYear(year)
                .forEach(System.out::println);
    }

    private void printAllBooksAfterYear(int year) {
        bookService
                .findAllBooksAfterYear(year)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}
