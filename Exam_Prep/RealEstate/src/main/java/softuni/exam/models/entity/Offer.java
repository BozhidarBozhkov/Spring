package softuni.exam.models.entity;

import org.hibernate.validator.internal.util.privilegedactions.LoadClass;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Table(name = "offers")
public class Offer extends BaseEntity{


    @Column(nullable = false)
    private BigDecimal price;


    //"dd/MM/yyyy" format
    @Column(name = "published_on", nullable = false)
    private LocalDate publishedOn;

    @ManyToOne
    private Apartment apartment;

    @ManyToOne
    private Agent agent;

    public Offer() {
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(LocalDate publishedOn) {
        this.publishedOn = publishedOn;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }


    @Override
    public String toString() {
        return String.format("Agent %s %s with offer №%d:%n" +
                "\t-Apartment area: %.2f%n" +
        "\t--Town: %s%n" +
        "\t---Price: %.2f$",
                this.agent.getFirstName(), this.agent.getLastName(), this.getId(),
        this.apartment.getArea(),
        this.apartment.getTown().getTownName(),
        this.price);
    }
}
