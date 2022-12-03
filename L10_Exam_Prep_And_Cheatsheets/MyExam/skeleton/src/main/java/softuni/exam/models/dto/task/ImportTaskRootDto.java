package softuni.exam.models.dto.task;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "tasks")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportTaskRootDto {
    @XmlElement(name = "task")
    private List<ImportTaskDto> tasks;

    public List<ImportTaskDto> getTasks() {
        return tasks;
    }
}
