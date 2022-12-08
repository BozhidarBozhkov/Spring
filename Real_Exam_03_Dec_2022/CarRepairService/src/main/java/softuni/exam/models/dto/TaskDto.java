package softuni.exam.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@XmlRootElement(name = "task")
@XmlAccessorType(XmlAccessType.FIELD)
public class TaskDto {

    @XmlElement
    private String date;

    @XmlElement
    @Positive
    private BigDecimal price;

    @XmlElement
    private CarIdDto car;

    @XmlElement
    private MechanicWithFirstNameDto mechanic;

    @XmlElement
    private PartIdDto part;
}
