package com.example.spring_intro_exercise.repositories;

import com.example.spring_intro_exercise.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> getBooksByReleaseDateAfter(LocalDate date);
    List<Book> getBooksByReleaseDateBefore(LocalDate date);

    int countBookByReleaseDateAfter(LocalDate date);
}
