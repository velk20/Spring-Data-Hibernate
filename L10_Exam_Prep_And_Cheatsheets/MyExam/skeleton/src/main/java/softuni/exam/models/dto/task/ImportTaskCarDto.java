package softuni.exam.models.dto.task;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ImportTaskCarDto {
    @XmlElement(name = "id")
    @NotNull
    private Long id;

    public Long getId() {
        return id;
    }
}
