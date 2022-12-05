package softuni.exam.instagraphlite.models.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ImportPictureDto {

    @NotNull
    private String path;

    @Min(500)
    @Max(60000)
    private double size;

    public ImportPictureDto() {
    }

    public String getPath() {
        return path;
    }

    public double getSize() {
        return size;
    }
}
