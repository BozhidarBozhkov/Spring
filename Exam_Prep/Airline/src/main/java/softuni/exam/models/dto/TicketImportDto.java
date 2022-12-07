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
import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@XmlRootElement(name = "ticket")
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketImportDto {

    @Size(min = 2)
    @NotNull
    @XmlElement(name = "serial-number")
    private String serialNumber;

    @Positive
    @NotNull
    @XmlElement
    private BigDecimal price;

    @NotNull
    @XmlElement(name = "take-off")
    private String takeoff;

    @XmlElement(name = "from-town")
    private FromTownNameDto fromTown;

    @XmlElement(name = "to-town")
    private ToTownNameDto toTown;

    @XmlElement
    private PassengerEmailDto passenger;

    @XmlElement
    private PlateRegisterNumberDto plane;

}
