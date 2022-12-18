package softuni.exam.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import softuni.exam.models.entity.StatusType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class PersonImportDto {

    @Email
    @NotNull
    private String email;

    @Size(min = 2, max = 30)
    @NotNull
    private String firstName;

    @Size(min = 2, max = 30)
    @NotNull
    private String lastName;

    @Size(min = 2, max = 13)
    private String phone;

    @NotNull
    private StatusType statusType;

    @NotNull
    private Long country;
}
