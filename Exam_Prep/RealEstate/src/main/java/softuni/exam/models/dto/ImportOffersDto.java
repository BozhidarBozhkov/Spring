package softuni.exam.models.dto;

import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement(name = "offer")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportOffersDto {

    @XmlElement
    @Positive
    private BigDecimal price;

    @XmlElement
    private AgentNameDto agent;

    @XmlElement
    private ApartmentIdDto apartment;

    @XmlElement
    private String publishedOn;

    public ImportOffersDto() {
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public AgentNameDto getAgent() {
        return agent;
    }

    public void setAgent(AgentNameDto agent) {
        this.agent = agent;
    }

    public void setApartment(ApartmentIdDto apartment) {
        this.apartment = apartment;
    }

    public void setPublishedOn(String publishedOn) {
        this.publishedOn = publishedOn;
    }

    public ApartmentIdDto getApartment() {
        return apartment;
    }

    public String getPublishedOn() {
        return publishedOn;
    }
}
