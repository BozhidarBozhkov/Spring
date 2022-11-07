package com.example.springintro;

import com.example.springintro.model.entity.Author;
import com.example.springintro.model.entity.Book;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;
    private final Scanner scanner;

    @Autowired
    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();
// TODO 1 and 2
        final String arg = scanner.nextLine();

        this.bookService.findFirstByTitle(arg);

        System.out.println(this.bookService.findCountOfBooksByBookTitleLongerThan(Integer.parseInt(arg)));


//        printAllBooksAfterYear(2000);
//        printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(1990);
//        printAllAuthorsAndNumberOfTheirBooks();
//        pritnALlBooksByAuthorNameOrderByReleaseDate("George", "Powell");

    }

    public void bookSearchByAuthorFirstNameStartingWith(String arg) {
        this.bookService.findAllByAuthorFirstNameStartingWith(arg)
                .stream().map(Book::getBookTitleAndAuthorFullNameFormat)
                .forEach(System.out::println);
    }

    public void bookSearchByContainingArgument(String arg) {

        this.bookService.findAllByTitleContaining(arg)
                .stream().map(Book::getTitle).forEach(System.out::println);
    }

    public void authorsSearch(String arg) {
        // final String arg = scanner.nextLine();
        this.authorService.findAllByFirstNameEndingWith(arg)
                .stream().map(Author::getFullName)
                .forEach(System.out::println);
    }

    public void booksReleasedBeforeDate() {
        this.bookService.findAllByReleaseDateBeforeYear(LocalDate.of(1992, 4, 12))
                .stream().map(Book::getBookTitleEditionTypeAndPriceFormat)
                .forEach(System.out::println);
    }

    public void notReleasedBook() {
        this.bookService.findAllByReleaseDateNot("2000")
                .stream().map(Book::getTitle)
                .forEach(System.out::println);
    }

    public void booksByPrice() {
        this.bookService.findAllByPriceLessThanOrPriceGreaterThan(BigDecimal.valueOf(5L), BigDecimal.valueOf(40L))
                .stream().map(Book::getBookTitleAndPriceFormat)
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
