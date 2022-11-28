package exam.model.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "towns")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportTownsRoot {
    @XmlElement(name = "town")
    private List<TownInformationDTO> towns;

    public List<TownInformationDTO> getTowns() {
        return towns;
    }

    public void setTowns(List<TownInformationDTO> towns) {
        this.towns = towns;
    }
}
