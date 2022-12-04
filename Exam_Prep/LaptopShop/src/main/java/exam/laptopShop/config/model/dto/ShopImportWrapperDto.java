package exam.laptopShop.config.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "shops")
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopImportWrapperDto {

    @XmlElement(name = "shop")
    private List<ShopImportDtos> shopImportDtos;

    public ShopImportWrapperDto() {
    }

    public List<ShopImportDtos> getShopImportDtos() {
        return shopImportDtos;
    }
}
