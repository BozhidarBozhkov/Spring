package productsShop.domain.dtos.products;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "sold-products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductsSoldWithCountDto {

    @XmlAttribute
    private Integer count;

    @XmlElement(name = "product")
    private List<ProductBasicInfoDto> products;

    public ProductsSoldWithCountDto(List<ProductBasicInfoDto> products) {
        this.products = products;
        this.count = products.size();
    }
}
