package softuni.exam.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@NoArgsConstructor
@XmlRootElement(name = "job")
@XmlAccessorType(XmlAccessType.FIELD)
public class JobDto {

    @Size(min = 2, max = 40)
    @NotNull
    @XmlElement(name = "jobTitle")
    private String title;

    @Min(10)
    @NotNull
    @XmlElement(name = "hoursAWeek")
    private Double hoursAWeek;

    @Min(300)
    @NotNull
    @XmlElement
    private Double salary;

    @Size(min = 5)
    @NotNull
    @XmlElement
    private String description;

    @NotNull
    @XmlElement(name = "companyId")
    private Long company;

}
