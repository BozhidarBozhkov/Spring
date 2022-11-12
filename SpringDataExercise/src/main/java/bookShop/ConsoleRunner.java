package bookShop;

import bookShop.domain.enums.AgeRestriction;
import bookShop.domain.enums.EditionType;
import bookShop.services.AuthorService;
import bookShop.services.BookService;
import bookShop.services.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Scanner;

import static bookShop.constants.Params.HIGH;
import static bookShop.constants.Params.LOW;


@Component
public class ConsoleRunner implements CommandLineRunner {

    private final Scanner scanner;
   // private final SeedService seedService;
    private final BookService bookService;
    private final AuthorService authorService;

    @Autowired
    public ConsoleRunner(SeedService seedService, BookService bookService, AuthorService authorService) {
     //   this.seedService = seedService;
        this.bookService = bookService;
        this.authorService = authorService;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run(String... args) throws Exception {

        /* filling the database */
        // this.seedService.seedAllData();

        /*P01. Books Titles by Age Restriction*/
        this.bookService.findAllByAgeRestriction(String.valueOf(AgeRestriction.MINOR));

        /*P02. Golden Books*/
        this.bookService.findAllByEditionTypeAndCopiesLessThan(EditionType.GOLD, 5000);

        /*P03. Books by Price*/
        this.bookService.findAllByPriceLessThanOrPriceGreaterThan(LOW, HIGH);

        /*P04. Not Released Books*/
        String year = scanner.nextLine();
        this.bookService.findAllByReleaseDateNot(year);

        /*P05. Books Released Before Date*/
        LocalDate date = getDateInput();
        this.bookService.findAllByReleaseDateBefore(date);

        /*P06. Authors Search*/
        String suffix = scanner.nextLine();
        this.authorService.findAllByFirstNameEndingWith(suffix);

        /*P07. Books Search*/
        String contains = scanner.nextLine();
        this.bookService.findAllByTitleContaining(contains);

        /*P08. Book Titles Search*/
        String prefix = scanner.nextLine();
        this.bookService.findAllByAuthorLastNameStartingWith(prefix);

        /*P09. Count Books*/
        int length = Integer.parseInt(scanner.nextLine());
        this.bookService.findCountOfBooksByBookTitleLongerThen(length);

        /*P10. Total Book Copies*/
        String name = scanner.nextLine();
        this.bookService.getBooksWrittenBy(name);

        /*P11. Reduced Book*/
        String title = scanner.nextLine();
        this.bookService.findFirstByTitle(title);

        /*P12. Increase Book Copies*/

        int copies = Integer.parseInt(scanner.nextLine());

        this.bookService.increaseBookCopies(getDateInput(), copies);

        /*P13. Remove Books */
        int books = Integer.parseInt(scanner.nextLine());
        this.bookService.deleteAllByCopiesLessThan(books);

    }

    private LocalDate getDateInput() {
        String[] dateInput = scanner.nextLine().split("-");
        int year1 = Integer.parseInt(dateInput[2]);
        int month = Integer.parseInt(dateInput[1]);
        int day = Integer.parseInt(dateInput[0]);

        return LocalDate.of(year1, month, day);
    }

}

