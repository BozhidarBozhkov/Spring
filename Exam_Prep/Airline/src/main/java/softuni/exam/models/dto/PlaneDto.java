package softuni.exam.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@NoArgsConstructor
@XmlRootElement(name = "plane")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlaneDto {

    @Size(min = 5)
    @XmlElement(name = "register-number")
    private String registerNumber;

    @Positive
    @XmlElement
    private int capacity;

    @Size(min = 2)
    @XmlElement
    private String airline;
}
