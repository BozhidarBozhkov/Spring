package softuni.exam.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@NoArgsConstructor
@XmlRootElement(name = "company")
@XmlAccessorType(XmlAccessType.FIELD)
public class CompanyDto {

    @Size(min = 2, max = 40)
    @NotNull
    @XmlElement(name = "companyName")
    private String name;

    @NotNull
    @XmlElement
    private String dateEstablished;

    @Size(min = 2, max = 30)
    @NotNull
    @XmlElement
    private String website;

    @NotNull
    @XmlElement(name = "countryId")
    private Long country;

}
