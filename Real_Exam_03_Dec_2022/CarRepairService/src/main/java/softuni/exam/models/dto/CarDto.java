package softuni.exam.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import softuni.exam.models.entity.CarType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@NoArgsConstructor
@XmlRootElement(name = "car")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarDto {

    @XmlElement
    @Size(min = 2, max = 30)
    private String carMake;

    @XmlElement
    @Size(min = 2, max = 30)
    private String carModel;

    @XmlElement
    @Positive
    private int year;

    @XmlElement
    @Size(min = 2, max = 30)
    private String plateNumber;

    @XmlElement
    @Positive
    private int kilometers;

    @XmlElement
    @Min(1)
    private double engine;

    @XmlElement
    @Enumerated(EnumType.STRING)
    private CarType carType;
}
