package exam.laptopShop.config.model.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement(name = "shop")
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopImportDtos {

    @XmlElement
    @Size(min = 4)
    private String address;

    @Min(value = 1)
    @Max(value = 50)
    @XmlElement(name = "employee-count")
    private Integer employeeCount;

    @XmlElement
    @Min(value = 20000)
    private BigDecimal income;

    @XmlElement
    @Size(min = 4)
    private String name;

    @XmlElement(name = "shop-area")
    @Min(value = 150)
    private Integer shopArea;

    @XmlElement
    private NameDto town;



    public String getAddress() {
        return address;
    }

    public Integer getEmployeeCount() {
        return employeeCount;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public String getName() {
        return name;
    }

    public Integer getShopArea() {
        return shopArea;
    }

    public NameDto getTown() {
        return town;
    }
}
