package com.example.springintro.repository;

import com.example.springintro.model.dto.BookInformationDTO;
import com.example.springintro.model.dto.BookSummary;
import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);

    List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);

    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(String author_firstName, String author_lastName);

    List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findAllByCopiesLessThan(int copies);

    @Query("select b from Book  as b " +
            "where b.price not between :price and :price1")
    List<Book> findAllByPriceNotBetween(BigDecimal price, BigDecimal price1);

    List<Book> findByReleaseDateBeforeOrReleaseDateAfter(LocalDate before, LocalDate after);

    List<Book> findAllByTitleContainingIgnoreCase(String str);

    @Query("select b from Book b " +
            "where lower(b.author.lastName) like :str%")
    List<Book> findAllByAuthorLastNameIgnoreCaseContaining(String str);

    @Query("select count(b) from Book b " +
            "where length(b.title) > :len")
    Long countDistinctByTitleLength(int len);

    @Query("select new com.example.springintro.model.dto.BookInformationDTO(b.title,b.editionType,b.ageRestriction,b" +
            ".price)" +
            "from Book as b " +
            "where b.title = :title")
    List<BookInformationDTO> findAllByTitle(String title);

    @Query("select b.title as title, " +
            "b.price as price from Book as b " +
            "where b.author.id = 7")
    List<BookSummary> getBookSummary();

    @Modifying
    @Query("update Book as b " +
            "set b.copies = b.copies + :copies " +
            "where b.releaseDate > :date")
    Integer increaseBookCopiesAfterDate(LocalDate date, int copies);

    @Modifying
    @Query("delete from Book as b " +
            "where b.copies < :copies")
    int removeBooksLowerThatGivenCopies(int copies);

    @Query(value = "CALL get_count_author_books(:firstName,:lastName);",nativeQuery = true)
    int getCountOfBooksByAuthor(String firstName, String lastName);
}
