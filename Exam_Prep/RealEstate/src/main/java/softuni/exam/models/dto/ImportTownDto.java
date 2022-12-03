package softuni.exam.models.dto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;


public class ImportTownDto {

    public String getTownName() {
        return townName;
    }

    @Size(min = 2)
    private String townName;

    @Positive
    private int population;

    public ImportTownDto() {
    }


    public int getPopulation() {
        return population;
    }
}
