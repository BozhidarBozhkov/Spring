package productsShop.domain.dtos.products;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductsSoldWithCountDto {

    private Integer count;

    private List<ProductBasicInfoDto> products;

    public ProductsSoldWithCountDto(List<ProductBasicInfoDto> products) {
        this.products = products;
        this.count = products.size();
    }
}
