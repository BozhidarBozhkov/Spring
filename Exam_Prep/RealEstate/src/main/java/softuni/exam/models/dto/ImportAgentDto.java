package softuni.exam.models.dto;

import com.google.gson.annotations.SerializedName;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class ImportAgentDto {

    @Size(min = 2)
    private String firstName;

    @Size(min = 2)
    private String lastName;

    @SerializedName("town")
    private String townName;

    @Email
    private String email;

    public ImportAgentDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getTownName() {
        return townName;
    }

    public String getEmail() {
        return email;
    }
}
