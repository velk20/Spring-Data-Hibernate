package com.example.spring_intro_exercise.services;

import com.example.spring_intro_exercise.models.Author;
import com.example.spring_intro_exercise.models.Book;
import com.example.spring_intro_exercise.models.Category;
import com.example.spring_intro_exercise.models.enums.AgeRestriction;
import com.example.spring_intro_exercise.models.enums.EditionType;
import com.example.spring_intro_exercise.repositories.AuthorRepository;
import com.example.spring_intro_exercise.repositories.BookRepository;
import com.example.spring_intro_exercise.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SeedServiceImpl implements SeedService {
    private static final String RESOURCE_PATH = "src/main/resources/files&task/";
    private static final String BOOKS_FILE_NAME = "books.txt";
    private static final String AUTHORS_FILE_NAME = "authors.txt";
    private static final String CATEGORIES_FILE_NAME = "categories.txt";

    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public SeedServiceImpl(AuthorService authorService, CategoryService categoryService, BookRepository bookRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository) {
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedAuthors() throws IOException {
        Files.readAllLines(Path.of(RESOURCE_PATH+AUTHORS_FILE_NAME))
                .stream()
                .filter(r -> !r.isBlank())
                .map(r->r.split("\\s+"))
                .map(names-> new Author(names[0],names[1]))
                .forEach(authorRepository::save);
    }

    @Override
    public void seedBooks() throws IOException {
        Files.readAllLines(Path.of(RESOURCE_PATH+BOOKS_FILE_NAME))
                .forEach(row -> {
                    String[] data = row.split("\\s+");

                    Author author = authorService.getRandomAuthor();
                    EditionType editionType = EditionType.values()[Integer.parseInt(data[0])];
                    LocalDate releaseDate = LocalDate.parse(data[1],
                            DateTimeFormatter.ofPattern("d/M/yyyy"));
                    int copies = Integer.parseInt(data[2]);
                    BigDecimal price = new BigDecimal(data[3]);
                    AgeRestriction ageRestriction = AgeRestriction
                            .values()[Integer.parseInt(data[4])];
                    String title = Arrays.stream(data)
                            .skip(5)
                            .collect(Collectors.joining(" "));
                    Set<Category> categories = categoryService.getRandomCategories();


                    Book book = new Book(title, editionType, price, copies,
                            releaseDate, ageRestriction, author, categories);

                    bookRepository.save(book);
                });
    }

    @Override
    public void seedCategories() throws IOException {
        Files.readAllLines(Path.of(RESOURCE_PATH+CATEGORIES_FILE_NAME))
                .stream()
                .filter(r -> !r.isBlank())
                .map(Category::new)
                .forEach(categoryRepository::save);
    }
}
