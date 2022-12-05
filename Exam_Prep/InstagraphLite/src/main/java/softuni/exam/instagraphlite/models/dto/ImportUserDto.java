package softuni.exam.instagraphlite.models.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ImportUserDto {

    @Size(min = 2, max = 18)
    @NotNull
    private String username;

    @Size(min = 4)
    @NotNull
    private String password;

    @NotNull
    private String profilePicture;

    public ImportUserDto() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getProfilePicture() {
        return profilePicture;
    }
}
