package exam.model.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "shops")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportShopsRoot {
    @XmlElement(name = "shop")
    private List<ShopInformationDTO> shops;

    public List<ShopInformationDTO> getShops() {
        return shops;
    }

    public void setShops(List<ShopInformationDTO> shops) {
        this.shops = shops;
    }
}
