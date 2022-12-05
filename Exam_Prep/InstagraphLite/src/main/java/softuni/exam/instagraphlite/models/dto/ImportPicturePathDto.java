package softuni.exam.instagraphlite.models.dto;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ImportPicturePathDto {

    @NotNull
    @XmlElement
    private String path;

    public ImportPicturePathDto() {
    }

    public String getPath() {
        return path;
    }
}
