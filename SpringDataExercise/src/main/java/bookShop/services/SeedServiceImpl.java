package bookShop.services;

import bookShop.domain.entities.Author;
import bookShop.domain.entities.Book;
import bookShop.domain.entities.Category;
import bookShop.domain.enums.AgeRestriction;
import bookShop.domain.enums.EditionType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static bookShop.constants.FilePath.*;

@Component
public class SeedServiceImpl implements SeedService {

    private AuthorService authorService;
    private BookService bookService;
    private CategoryService categoryService;

    public SeedServiceImpl(AuthorService authorService, BookService bookService, CategoryService categoryService) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedAuthors() throws IOException {
        if (!this.authorService.isDataSeeded()) {
            this.authorService
                    .seedAuthors(Files.readAllLines(Path.of(RESOURCE_PATH + AUTHORS_FILE_NAME))
                            .stream()
                            .filter(s -> !s.isBlank())
                            .map(firstAndLastName -> Author.builder()
                                    .firstName(firstAndLastName.split(" ")[0])
                                    .lastName(firstAndLastName.split(" ")[1])
                                    .build())
                            .collect(Collectors.toList()));
        }
    }

    @Override
    public void seedBooks() throws IOException {
        if (!this.bookService.isDataSeeded()) {
            final List<Book> books = Files.readAllLines(Path.of(RESOURCE_PATH + BOOKS_FILE_NAME))
                    .stream()
                    .filter(s -> !s.isBlank())
                    .map(row -> {
                        String[] data = row.split("\\s+");
                        String title = Arrays.stream(data)
                                .skip(5)
                                .collect(Collectors.joining(" "));

                        return Book.builder()
                                .title(title)
                                .editionType(EditionType.values()[Integer.parseInt(data[0])])
                                .price(new BigDecimal(data[3]))
                                .releaseDate(LocalDate.parse(data[1], DateTimeFormatter.ofPattern("d/M/yyyy")))
                                .ageRestriction(AgeRestriction.values()[Integer.parseInt(data[4])])
                                .author(this.authorService.getRandomAuthor())
                                .categories(this.categoryService.getRandomCategories())
                                .copies(Integer.parseInt(data[2]))
                                .build();

                    })
                    .collect(Collectors.toList());

            this.bookService.seedBooks(books);
        }
    }

    @Override
    public void seedCategory() throws IOException {
        if (!this.categoryService.isDataSeeded()) {
            this.categoryService.seedCategories(Files.readAllLines(Path.of(RESOURCE_PATH + CATEGORIES_FILE_NAME))
                    .stream()
                    .filter(s -> !s.isBlank())
                    .map(name -> Category.builder()
                            .name(name)
                            .build())
                    .collect(Collectors.toList()));
        }
    }

}
