package softuni.exam.instagraphlite.models.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "post")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportPostDto {

    @Size(min = 21)
    @XmlElement
    private String caption;

    @NotNull
    @XmlElement(name = "user")
    private ImportUsernameDto usernames;

    @NotNull
    @XmlElement(name = "picture")
    private ImportPicturePathDto pictures;

    public ImportPostDto() {
    }

    public String getCaption() {
        return caption;
    }

    public ImportUsernameDto getUsernames() {
        return usernames;
    }

    public ImportPicturePathDto getPictures() {
        return pictures;
    }
}
