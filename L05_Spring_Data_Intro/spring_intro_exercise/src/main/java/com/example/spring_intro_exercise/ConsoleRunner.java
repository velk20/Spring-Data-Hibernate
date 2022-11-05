package com.example.spring_intro_exercise;

import com.example.spring_intro_exercise.models.Author;
import com.example.spring_intro_exercise.models.Book;
import com.example.spring_intro_exercise.repositories.AuthorRepository;
import com.example.spring_intro_exercise.repositories.BookRepository;
import com.example.spring_intro_exercise.services.AuthorService;
import com.example.spring_intro_exercise.services.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Component
public class ConsoleRunner implements CommandLineRunner {


   private final BookRepository bookRepository;
   private final AuthorRepository authorRepository;
    private final SeedService seedService;
    private final AuthorService authorService;

    @Autowired
    public ConsoleRunner(AuthorService authorService, BookRepository bookRepository, SeedService seedService,
                         AuthorRepository authorRepository) {
        this.authorService = authorService;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.seedService = seedService;
    }

    @Override
    public void run(String... args) throws Exception {

//        seedService.seedAll();

//        this._01_booksAfter2000();

//        this._02_authorsWIthBookAfter();

//        this._03_authorsWithBooksCount();

        this._04_booksByGeorgePowell();

    }

    private void _01_booksAfter2000(){
        LocalDate myDate = LocalDate.of(2000, 12, 31);
        List<Book> booksByReleaseDateAfter = bookRepository
                .getBooksByReleaseDateAfter(myDate);

        booksByReleaseDateAfter.forEach(e-> System.out.println(e.getTitle()));
        System.out.println("Count: "+bookRepository.countBookByReleaseDateAfter(myDate));
    }

    private void _02_authorsWIthBookAfter(){
        //Task2
        LocalDate myDate = LocalDate.of(1990, 1, 1);
        List<Author> authors = authorRepository.findDistinctByBooksReleaseDateBefore(myDate);
        authors.forEach(e -> System.out.println(e.getFirstName() + " " + e.getLast_name()));
        System.out.println(authors.size());
    }

    private void _03_authorsWithBooksCount(){
        //Task3
        List<Author> distinctByBooksOrderByBooksDesc = authorRepository.findAll();
        distinctByBooksOrderByBooksDesc
                .stream()
                .sorted((l, r) -> r.getBooks().size() - l.getBooks().size())
                .forEach(e -> System.out.println(
                        e.getFirstName() + " " + e.getLast_name() + " " + e.getBooks().size()
                ));

    }

    private void _04_booksByGeorgePowell() {

    }
}
