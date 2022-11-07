package com.example.springintro.repository;

import com.example.springintro.model.dto.BookInformation;
import com.example.springintro.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);

    List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);

    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(String author_firstName, String author_lastName);

    Optional<List<Book>> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal low, BigDecimal high);

    Optional<List<Book>> findAllByReleaseDateNot(String date);

    Optional<List<Book>> findAllByReleaseDateBeforeYear(LocalDate date);

    Optional<List<Book>> findAllByTitleContaining(String contains);

    Optional<List<Book>> findAllByAuthorFirstNameStartingWith(String prefix);

    @Query("SELECT COUNT(b.id) FROM Book b WHERE length(b.title) > :length")
    Optional<Integer> findCountOfBooksByBookTitleLongerThan(Integer length);

    @Query("select new com.example.springintro.model.dto.BookInformation(b.title, b.editionType, b.ageRestriction, b.price) from Book b where b.title = :title")
    Optional<BookInformation> findFirstByTitle(String title);
}
