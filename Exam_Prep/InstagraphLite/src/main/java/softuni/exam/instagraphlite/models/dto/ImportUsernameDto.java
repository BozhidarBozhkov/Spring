package softuni.exam.instagraphlite.models.dto;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ImportUsernameDto {

    @NotNull
    @XmlElement
    private String username;

    public ImportUsernameDto() {
    }

    public String getUsername() {
        return username;
    }
}
