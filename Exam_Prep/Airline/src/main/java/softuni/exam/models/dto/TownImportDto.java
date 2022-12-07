package softuni.exam.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class TownImportDto {

    @Size(min = 2)
    @NotNull
    private String name;

    @Positive
    @NotNull
    private int population;

    @NotNull
    private String guide;
}
