package bookShop.services;

import bookShop.domain.entities.Author;

import java.time.LocalDate;
import java.util.List;

public interface AuthorService {

    void seedAuthors(List<Author> authors);

    boolean isDataSeeded();

    Author getRandomAuthor();

    List<Author> findDistinctByBooksBefore(LocalDate date);

    List<Author> findAllOrderByBooks();

    List<Author> findAllByFirstNameEndingWith(String suffix);



}