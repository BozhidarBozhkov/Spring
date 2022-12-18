package softuni.exam.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class CountryImportDto {

    @Size(min = 2, max = 30)
    @NotNull
    private String name;

    @Size(min = 2, max = 19)
    @NotNull
    private String countryCode;

    @Size(min = 2, max = 19)
    @NotNull
    private String currency;
}
