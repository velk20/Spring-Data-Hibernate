package com.example.springintro.repository;

import com.example.springintro.model.dto.BookCopiesDTO;
import com.example.springintro.model.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT a FROM Author a ORDER BY a.books.size DESC")
    List<Author> findAllByBooksSizeDESC();

    List<Author> findAllByFirstNameEndingWith(String endString);

    @Query("select new com.example.springintro.model.dto.BookCopiesDTO(a,sum(b.copies)) from Author a " +
            "inner join a.books b group by a.id")
    List<BookCopiesDTO> findAllByCountCopies();
}
