package bookShop.domain.dto;

import bookShop.domain.enums.AgeRestriction;
import bookShop.domain.enums.EditionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class BookInformation {

    private String title;

    private EditionType editionType;

    private AgeRestriction ageRestriction;

    private BigDecimal price;

    @Override
    public String toString() {
        return this.title + " "
                + this.editionType.name() + " "
                + this.ageRestriction.name() + " "
                + this.price;
    }

}
