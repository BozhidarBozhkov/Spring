package bookShop.domain.entities;

import bookShop.domain.enums.AgeRestriction;
import bookShop.domain.enums.EditionType;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
public class Book extends BaseEntity{

    @Column(nullable = false, length = 50)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(name = "edition_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EditionType editionType;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private int copies;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "age_restriction", nullable = false)
    @Enumerated(EnumType.STRING)
    private AgeRestriction ageRestriction;

    @ManyToOne
    // A book can have one author
    private Author author;

    @ManyToMany
    // A book can have many categories. Each category can be placed on many books
    private Set<Category> categories;

    public String getBookTitleAndPriceFormat() {
        return this.title + " - $" + this.price;
    }

    public String getBookTitleEditionTypeAndPriceFormat() {
        return this.title + " " + this.editionType.name() + " " + this.price;
    }

    public String getBookTitleReleaseDateCopiesFormat() {
        return this.title + " " + this.releaseDate + " " + this.copies;
    }

    public String getBookTitleAndAuthorFullNameFormat() {
        return this.title + " (" + this.author.getFullName() + ")";
    }

    public String getReducedBookInformation() {
        StringBuilder sb = new StringBuilder();

        sb.append(this.getTitle()).append(" ");
        sb.append(this.getEditionType()).append(" ");
        sb.append(this.getAgeRestriction()).append(" ");
        sb.append(this.getPrice());

        return sb.toString();
    }

    public String getAuthorsFirstLastNameAndCopiesFormatted() {
        StringBuilder sb = new StringBuilder();

        sb.append(this.getAuthor().getLastName()).append(" ");
        sb.append(this.getAuthor().getFirstName()).append(" - ");
        sb.append(this.getCopies());

        return sb.toString();
    }

}
