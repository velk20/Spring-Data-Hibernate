package softuni.exam.instagraphlite.models.pictures;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ImportJsonPictureDTO {
    @NotNull
    private String path;
    @Min(500)
    @Max(60000)
    private double size;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }
}
