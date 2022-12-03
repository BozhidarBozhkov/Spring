package softuni.exam.models.dto;

import softuni.exam.models.entity.ApartmentType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "apartment")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportApartmentsDto {

    @XmlElement
    @NotNull
    private ApartmentType apartmentType;

    @XmlElement
    @Min(40)
    private Double area;

    @XmlElement(name = "town")
    private String townName;

    public ImportApartmentsDto() {
    }

    public ApartmentType getApartmentType() {
        return apartmentType;
    }

    public Double getArea() {
        return area;
    }

    public String getTownName() {
        return townName;
    }
}
