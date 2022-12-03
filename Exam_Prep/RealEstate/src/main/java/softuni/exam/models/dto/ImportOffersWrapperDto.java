package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "offers")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportOffersWrapperDto {

    @XmlElement(name = "offer")
    public List<ImportOffersDto> offers;

    public ImportOffersWrapperDto() {
    }

    public List<ImportOffersDto> getOffers() {
        return offers;
    }

    public void setOffers(List<ImportOffersDto> offers) {
        this.offers = offers;
    }
}

